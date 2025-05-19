import React, { useState, useEffect, useRef } from "react";
import axios from "axios";

function HQPaymentCheck({ filteredRows, isFiltered }) {
    const tableRef = useRef(null);
    const [rows, setRows] = useState([]);
    const [rows2, setRows2] = useState([]);

    const [selectedOrderId, setSelectedOrderId] = useState(null);
    const [showOrderDetails, setShowOrderDetails] = useState(false);

    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;

    const [mainCurrentPage, setMainCurrentPage] = useState(1);
    const mainItemsPerPage = 5; // 한 페이지당 항목 수



    useEffect(() => {
        if (!isFiltered) {
            axios.get("http://localhost:8080/HQMain/payment")
                .then(res => {
                    console.log("서버 응답:", res.data);
                    setRows(res.data);
                })
                .catch(err => {
                    console.error("데이터 불러오기 실패:", err);
                });

            axios.get("http://localhost:8080/HQMain/paymentDetail")
                .then(res => {
                    console.log("서버 응답:", res.data);
                    setRows2(res.data);
                })
                .catch(err => {
                    console.error("데이터 불러오기 실패:", err);
                });
        }
    }, [isFiltered]);

    useEffect(() => {
        if (isFiltered) {
            setRows(filteredRows);
            setRows2(filteredRows);
        }
    }, [filteredRows, isFiltered]);

    // 1. 결재 or 반려인 항목 필터링
    const approvedOrRejectedRows = rows.filter(
        row => ["결재", "반려"].includes(row.orderStatus)
    );

    const groupedRows = Object.values(
        approvedOrRejectedRows.reduce((acc, row) => {
            if (!acc[row.orderId]) {
                acc[row.orderId] = {
                    orderId: row.orderId,
                    orderDate: row.orderDate,
                    orderStatus: row.orderStatus,
                    partNames: [],
                };
            }
            acc[row.orderId].partNames.push(row.partName);
            return acc;
        }, {})
    );


    const displayRows = groupedRows.map(group => {
        const firstPartName = group.partNames[0];
        const total = group.partNames.length;

        return {
            ...group,
            displayPartName: total > 1 ? `${firstPartName} 외 ${total - 1}종` : firstPartName,
        };
    });

    const handleRowClick = (orderId) => {
        setSelectedOrderId(orderId);
        setShowOrderDetails(true);
        if (tableRef.current) {
            tableRef.current.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    };

    const closeModal = () => {
        setShowOrderDetails(false);
        setSelectedOrderId(null);
    };


    const filteredDetailRows = rows2.filter(row => row.orderId === selectedOrderId);


    // 대리점 주문 내역 페이징
    const mainTotalPages = Math.ceil(displayRows.length / mainItemsPerPage);
    const mainIndexOfLastItem = mainCurrentPage * mainItemsPerPage;
    const mainIndexOfFirstItem = mainIndexOfLastItem - mainItemsPerPage;
    const mainCurrentItems = displayRows.slice(mainIndexOfFirstItem, mainIndexOfLastItem);

    // 모달 페이징
    const totalPages = Math.ceil(filteredDetailRows.length / itemsPerPage);
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = filteredDetailRows.slice(indexOfFirstItem, indexOfLastItem);

    return (
        <div>
            <div className="p-4 mt-3 bg-light w-100 overflow-auto">
                <h2 className="h5 fw-bold mb-3">대리점 주문 내역</h2>
                <table className="table table-bordered" ref={tableRef}>
                    <thead className="table-info">
                    <tr>
                        <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FFFF" }}>주문번호</th>
                        <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FFFF" }}>주문일자</th>
                        <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FFFF" }}>부품명</th>
                        <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FFFF" }}>주문현황</th>
                    </tr>
                    </thead>
                    <tbody>
                    {mainCurrentItems.length === 0 ? (
                        <tr>
                            <td colSpan="4" className="text-center">처리된 것이 없습니다.</td>
                        </tr>
                    ) : (
                      mainCurrentItems.map((row, i) => (
                            <tr key={i} onClick={() => handleRowClick(row.orderId)} style={{ cursor: 'pointer' }}>
                                <td className="text-center align-middle">{row.orderId}</td>
                                <td className="text-center align-middle">{row.orderDate}</td>
                                <td className="text-center align-middle">{row.displayPartName}</td>
                                <td className="text-center align-middle" style={{ color: row.orderStatus === '반려' ? 'red' : 'black' }}>{row.orderStatus}</td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>


                {mainTotalPages > 1 && (
                  <div className="mt-4">
                      <nav>
                          <ul className="pagination justify-content-center">
                              <li className={`page-item ${mainCurrentPage === 1 ? "disabled" : ""}`}>
                                  <button className="page-link" onClick={() => mainCurrentPage > 1 && setMainCurrentPage(mainCurrentPage - 1)}>
                                      &lt;
                                  </button>
                              </li>

                              {Array.from({ length: mainTotalPages }, (_, i) => (
                                <li key={i + 1} className={`page-item ${mainCurrentPage === i + 1 ? "active" : ""}`}>
                                    <button className="page-link" onClick={() => setMainCurrentPage(i + 1)}>
                                        {i + 1}
                                    </button>
                                </li>
                              ))}

                              <li className={`page-item ${mainCurrentPage === mainTotalPages ? "disabled" : ""}`}>
                                  <button className="page-link" onClick={() => mainCurrentPage < mainTotalPages && setMainCurrentPage(mainCurrentPage + 1)}>
                                      &gt;
                                  </button>
                              </li>
                          </ul>
                      </nav>
                  </div>
                )}




            </div>
            {showOrderDetails && (
                <div className="modal d-block" tabIndex="-1" role="dialog" style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
                    <div className="modal-dialog modal-xl" role="document">
                        <div className="modal-content">
                            <div className="modal-header" style={{ backgroundColor: '#CFE2FFFF' }}>
                                <h5 className="modal-title">상세 내역</h5>
                                {/*<button type="button" className="btn-close" onClick={closeModal}></button>*/}
                            </div>
                            <div className="modal-body">
                                <table className="table table-bordered">
                                    <thead className="table-info">
                                    <tr>
                                        <th className="text-center align-middle" rowSpan="2" style={{ width: '130px', backgroundColor: "#CFE2FFFF" }}>대리점 ID</th>
                                        <th className="text-center align-middle" colSpan="2" style={{ backgroundColor: "#CFE2FFFF" }}>부품</th>
                                        <th className="text-center align-middle" colSpan="2" style={{ backgroundColor: "#CFE2FFFF" }}>가격</th>
                                        <th className="text-center align-middle" rowSpan="2" style={{ width: '130px', backgroundColor: "#CFE2FFFF" }}>주문일자</th>
                                    </tr>
                                    <tr>
                                        <th className="text-center align-middle" style={{ width: '130px', backgroundColor: "#CFE2FFFF" }}>부품 Code</th>
                                        <th className="text-center align-middle" style={{ width: '130px', backgroundColor: "#CFE2FFFF" }}>부품명</th>
                                        <th className="text-center align-middle" style={{ width: '130px', backgroundColor: "#CFE2FFFF" }}>수량</th>
                                        <th className="text-center align-middle" style={{ width: '130px', backgroundColor: "#CFE2FFFF" }}>비용</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {currentItems.length === 0 ? (
                                        <tr>
                                            <td colSpan="6" className="text-center">상세 내역이 없습니다.</td>
                                        </tr>
                                    ) : (
                                        currentItems.map((row, i) => (
                                            <tr key={i}>
                                                <td className="text-center">{row.branchId}</td>
                                                <td className="text-center">{row.partId}</td>
                                                <td className="text-center">{row.partName}</td>
                                                <td className="text-center">{row.orderItemQuantity}</td>
                                                <td className="text-center">{row.orderItemPrice.toLocaleString()} (원)</td>
                                                <td className="text-center">{row.orderDate}</td>
                                            </tr>
                                        ))
                                    )}
                                    </tbody>
                                </table>
                                {totalPages > 1 && (
                                    <div className="d-flex justify-content-center align-items-center mt-3 gap-3">
                                        <button
                                            className="btn btn-outline-primary"
                                            onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
                                            disabled={currentPage === 1}
                                        >
                                            이전
                                        </button>
                                        <span>{currentPage} / {totalPages}</span>
                                        <button
                                            className="btn btn-outline-primary"
                                            onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))}
                                            disabled={currentPage === totalPages}
                                        >
                                            다음
                                        </button>
                                    </div>
                                )}
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-primary" onClick={closeModal}>닫기</button>
                            </div>
                        </div>
                    </div>
                </div>

            )}
        </div>
    );
}

export default HQPaymentCheck;
