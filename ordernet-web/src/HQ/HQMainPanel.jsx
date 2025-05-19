import React, { useState, useEffect } from "react";
import axios from "axios";
import bellIcon from '../assets/bellIcon.png';
import './AlertBox.css';

function HQMainPanel( { filteredRows, isFiltered }) {

  const [showApprovalModal, setShowApprovalModal] = useState(false);
  const [approvalData, setApprovalData] = useState([]);

  const [selectedOrderId, setSelectedOrderId] = useState(null);  // 행 클릭

  const [showOrderDetails, setShowOrderDetails] = useState(false); // 발주 내역 숨기기

  // 페이지네이션 상태
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10); // itemsPerPage는 고정값이라 useState 안 해도 됨


  const handleOpenModal = () => {
    const selectedRows = rows2.filter(row => row.orderId === selectedOrderId);
    setApprovalData(selectedRows.length > 0 ? selectedRows : []);
    setShowApprovalModal(true);
  };

  const handleCloseModal = () => setShowApprovalModal(false);

  const [denyReason, setDenyReason] = useState("");


  const [rows, setRows] = useState([]);

  const [rows2, setRows2] = useState([]);

  const pendingRows = rows.filter(row => row.orderStatus === "승인 대기");

  // 1. 페이지 데이터 계산
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentData = rows.slice(indexOfFirstItem, indexOfLastItem);

// 2. 전체 페이지 수 계산
  const totalPages = Math.ceil(rows.length / itemsPerPage);

// 3. 페이지네이션 표시 범위 계산
  const maxPageNumbers = 5;
  const pageGroup = Math.floor((currentPage - 1) / maxPageNumbers);
  let startPage = pageGroup * maxPageNumbers + 1;
  let endPage = Math.min(startPage + maxPageNumbers - 1, totalPages);
  if (totalPages <= maxPageNumbers) {
    startPage = 1;
    endPage = totalPages;
  }


  useEffect(() => {

    if (!isFiltered) {
      axios.get("http://localhost:8080/HQMain/order-item-info")
          .then(res => {
            console.log("order-item-info 응답:", res.data);
            setRows(res.data);
          })
          .catch(err => {
            console.error("order-item-info 불러오기 실패:", err);
          });

      axios.get("http://localhost:8080/HQMain/orderList")
          .then(res => {
            console.log("서버 응답:", res.data); // 👉 콘솔 출력
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

  const [orderCount, setOrderCount] = useState(0); // 주문 개수
  const [isNotificationVisible, setIsNotificationVisible] = useState(false); // 알림 상태

  useEffect(() => {
    const intervalId = setInterval(() => {
      // 서버로 주문 개수 요청
      axios.get("http://localhost:8080/HQMain/orderCount")// 서버에서 새로운 주문 개수 요청
          .then((response) => {
            const newOrderCount = response.data;

            // 새로운 주문이 있으면 알림 띄우기
            if (newOrderCount > 0) {
              setOrderCount(prevCount => prevCount + newOrderCount); // 주문 개수 업데이트
              setIsNotificationVisible(true); // 알림 표시
            }
          })
          .catch((error) => {
            console.error('서버 요청 실패:', error);
          });
    }, 5000); // 5초마다 요청

    // 컴포넌트 언마운트 시 interval 정리
    return () => clearInterval(intervalId);
  }, []); // 빈 배열로 인해 한번만 실행

  const closeNotification = () => {
    setIsNotificationVisible(false); // 알림 닫기
  };

  // 페이지 번호 렌더링 함수
  const renderPageNumbers = () => {
    const pageNumbers = [];
    for (let i = startPage; i <= endPage; i++) {
      pageNumbers.push(
          <li key={i} className={`page-item ${currentPage === i ? "active" : ""}`}>
            <button className="page-link" onClick={() => setCurrentPage(i)}>
              {i}
            </button>
          </li>
      );
    }
    return pageNumbers;
  };


  return (
      <div>
        <div>
          {isNotificationVisible && (
              <div className="alert-box">
                <img src={bellIcon} alt="bell icon" className="bell-icon" />
                <p>새로운 주문 {orderCount}건 있습니다!</p>
                <button onClick={closeNotification}>닫기</button>
              </div>
          )}
        </div>



        <div className="p-4 mt-3 bg-light w-100" >
          <h2 className="h5 fw-bold mb-3">미결재 리스트</h2>

          <div style={{ width: '100%' }}>
            {/* 반응형 테이블 래퍼 */}
            <div className="table-responsive">
              <table className="table table-bordered" style={{ tableLayout: 'fixed', width: '100%', marginBottom: 0 }}>
                <colgroup>
                  <col style={{ width: '130px' }} />
                  <col style={{ width: '130px' }} />
                  <col style={{ width: '130px' }} />
                  <col style={{ width: '130px' }} />
                  <col style={{ width: '130px' }} />
                  <col style={{ width: '130px' }} />
                </colgroup>
                <thead className="table-info">
                <tr>
                  <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }} rowSpan="2">주문번호</th>
                  <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }} rowSpan="2">대리점 ID</th>
                  <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }} colSpan="2">일자</th>
                  <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }} rowSpan="2">가격</th>
                  <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }} rowSpan="2">주문현황</th>
                </tr>
                <tr>
                  <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }}>주문일자</th>
                  <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }}>도착일자</th>
                </tr>
                </thead>
                <tbody>
                {pendingRows.length === 0 ? (
                    <tr>
                      <td colSpan="6" className="text-center">미결재 리스트가 없습니다.</td>
                    </tr>
                ) : (
                    Array.from(new Map(pendingRows.map(row => [row.orderId, row])).values()).map((row, i) => (
                        <tr
                            key={i}
                            onClick={() => {
                              if (selectedOrderId === row.orderId) {
                                setSelectedOrderId(null);
                                setShowOrderDetails(false);
                              } else {
                                setSelectedOrderId(row.orderId);
                                setShowOrderDetails(true);
                              }
                            }}
                            className="cursor-pointer"
                        >
                          <td className="text-center align-middle" style={{ backgroundColor: selectedOrderId === row.orderId ? "#E0F7FA" : "transparent" }}>{row.orderId}</td>
                          <td className="text-center align-middle" style={{ backgroundColor: selectedOrderId === row.orderId ? "#E0F7FA" : "transparent" }}>{row.branchId}</td>
                          <td className="text-center align-middle" style={{ backgroundColor: selectedOrderId === row.orderId ? "#E0F7FA" : "transparent" }}>{row.orderDate}</td>
                          <td className="text-center align-middle" style={{ backgroundColor: selectedOrderId === row.orderId ? "#E0F7FA" : "transparent" }}>{row.orderDueDate}</td>
                          <td className="text-center align-middle" style={{ backgroundColor: selectedOrderId === row.orderId ? "#E0F7FA" : "transparent" }}>
                            {row.orderPrice.toLocaleString()} (원)
                          </td>
                          <td className="text-center align-middle" style={{
                            backgroundColor: selectedOrderId === row.orderId ? "#E0F7FA" : "transparent",
                            color: row.orderStatus === '반려' ? 'red' : 'black',
                          }}>{row.orderStatus}</td>
                        </tr>
                    ))
                )}
                </tbody>
              </table>
            </div>

            {/*  페이지네이션: 테이블 바깥, 항상 아래에 위치 */}
            {totalPages > 1 && (
                <div className="mt-4">
                  <nav>
                    <ul className="pagination justify-content-center">
                      <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => currentPage > 1 && setCurrentPage(currentPage - 1)}>&lt;</button>
                      </li>
                      {renderPageNumbers()}
                      <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => currentPage < totalPages && setCurrentPage(currentPage + 1)}>&gt;</button>
                      </li>
                    </ul>
                  </nav>
                </div>
            )}
          </div>

        </div>


        <hr></hr>

        {showOrderDetails && (
            <>
            <div className="p-4 mt-3 bg-light w-100">
              <h2 className="h5 fw-bold mt-1 mb-3">발주 내역</h2>

              <div style={{ width: '100%', overflow: 'hidden' }}>
                {/* 헤더 테이블 */}
                <div style={{ overflowY: 'scroll', overflowX: 'hidden', scrollbarWidth: 'none', width: '99%' }}>
                  <table className="table table-bordered" style={{ tableLayout: 'fixed', width: '100%', marginBottom: 0 }}>
                    <colgroup>
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                    </colgroup>
                    <thead className="table-info">
                    <tr>
                      <th className="text-center align-middle" rowSpan="2" style={{ backgroundColor: "#CFE2FF" }}>대리점 ID</th>
                      <th className="text-center align-middle" colSpan="2" style={{ backgroundColor: "#CFE2FF" }}>부품</th>
                      <th className="text-center align-middle" colSpan="2" style={{ backgroundColor: "#CFE2FF" }}>가격</th>
                      <th className="text-center align-middle" rowSpan="2" style={{ backgroundColor: "#CFE2FF" }}>주문일자</th>
                    </tr>
                    <tr>
                      <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }}>부품 Code</th>
                      <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }}>부품명</th>
                      <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }}>수량</th>
                      <th className="text-center align-middle" style={{ backgroundColor: "#CFE2FF" }}>비용</th>
                    </tr>
                    </thead>
                  </table>
                </div>

                {/* 바디 테이블 */}
                <div style={{ maxHeight: '400px', overflowY: 'scroll', width: '100%' }}>
                  <table className="table table-bordered" style={{ tableLayout: 'fixed', width: 'calc(100% - 1px)' }}>
                    <colgroup>
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                      <col style={{ width: '130px' }} />
                    </colgroup>
                    <tbody>
                    {rows2.length === 0 ? (
                        <tr>
                          <td colSpan="6" className="text-center">발주 내역이 없습니다.</td>
                        </tr>
                    ) : (
                        rows2.filter(row => row.orderId === selectedOrderId).map((row, i) => (
                            <tr key={i} style={{ backgroundColor: selectedOrderId === row.orderId ? '#ffe8a1' : '' }}>
                              <td className="text-center align-middle">{row.branchId}</td>
                              <td className="text-center align-middle">{row.partId}</td>
                              <td className="text-center align-middle">{row.partName}</td>
                              <td className="text-center align-middle">{row.orderItemQuantity}</td>
                              <td className="text-center align-middle">{row.orderItemPrice.toLocaleString()} (원)</td>
                              <td className="text-center align-middle">{row.orderDate}</td>
                            </tr>
                        ))
                    )}
                    </tbody>
                  </table>
                </div>
              </div>

              <div className="mt-3 text-end">
                <button className="btn" style={{ backgroundColor: "#CFE2FF" }} type="button" onClick={handleOpenModal}>
                  결재
                </button>
              </div>
            </div>
              <hr/>
            </>
        )}


        {showApprovalModal && <ApprovalModal onClose={handleCloseModal} rows={rows} rows2={approvalData}  denyReason={denyReason} setDenyReason={setDenyReason}/>}
      </div>

  );

}

function ApprovalModal({onClose, rows, rows2, denyReason, setDenyReason}) {

  if (!rows || rows.length === 0) return null;

  if (!rows2 || rows2.length === 0) return null;

  // 중복 제거 및 필드별 콤마로 구분된 값 생성
  // const uniqueByField1 = (field) => [...new Set(rows.map(row => row[field]))].join(', ');
  const uniqueByField = (field) => [...new Set(rows2.map(row => row[field]))].join(', ');

  const branchId = uniqueByField('branchId');
  const partId = uniqueByField('partId');
  const branchName = uniqueByField('branchName');
  const partName = uniqueByField('partName');
  const orderDate = uniqueByField('orderDate');

  const handleApproval = (type) => {
    const status = type === '결재' ? '결재' : '반려';
    const finalDenyReason = type === '결재' ? '결재되었습니다.' : denyReason;


    if (rows2.length === 0) {
      alert("선택된 주문이 없습니다.");
      return;
    }

    // 반려일 때 이유 미입력 시 알림
    if (type === '반려' && denyReason.trim() === '') {
      alert("반려 이유를 입력하세요.");
      return;
    }

    let requestBody;

    if (rows2.length === 1) {
      //  단일 처리용: orderId 와 partId 에 해당하는 것 하나만 전송
      requestBody = {
        orderId: rows2[0].orderId,
        orderStatus: status,
        orderDeny: finalDenyReason,
        // orderItemStatus: orderItemStatus
      };
    } else {
      //  복수 처리용: orderIdList 전송
      const orderIdList = rows2.map(row => row.orderId);
      requestBody = {
        orderIdList: orderIdList,
        orderStatus: status,
        orderDeny: finalDenyReason,
      };
    }

    // 결제 or 반려 처리
    axios.post("http://localhost:8080/HQMain/update", requestBody)
        .then(res => {
          console.log(res.data);
          alert(`${type} 처리 완료`);
          onClose(); // 모달 닫기
        })
        .catch(err => {
          console.error(`${type} 처리 실패`, err);
          alert(`${type} 처리 중 오류 발생`);
        });
  };



  return (
      <div className="approval-modal modal show d-block" tabIndex={-1} role="dialog">
        <div className="modal-dialog modal-lg" role="document">
          <div className="modal-content">
            <div className="modal-header" style={{ backgroundColor: '#CFE2FF' }}>
              <h5 className="modal-title">결재</h5>
              {/*<button className="custom-x-button" onClick={onClose}>×</button>*/}
              <button type="button" className="btn-close" onClick={onClose} aria-label="Close">X</button>
            </div>
            <div className="modal-body">
              <table className="table table-bordered">
                <tbody>
                <tr>
                  <th style={{ whiteSpace: "nowrap" }}>대리점 ID</th>
                  <td style={{ whiteSpace: "nowrap" }}>{branchId}</td>
                  <th style={{ whiteSpace: "nowrap" }}>부품 Code</th>
                  <td>{partId}</td>
                </tr>
                <tr>
                  <th style={{ whiteSpace: "nowrap" }}>지점명</th>
                  <td colSpan={3}>{branchName}</td>
                </tr>
                <tr>
                  <th>부품명</th>
                  <td colSpan={3}>{partName}</td>
                </tr>
                <tr>
                  <th>주문일자</th>
                  <td colSpan={3}>{orderDate}</td>
                </tr>
                </tbody>
              </table>
              <div className="form-group">
                <label className="form-label fw-bold">반려 이유</label>
                <input type="text" className="form-control" placeholder="무슨무슨이유로 인해 반려합니다." value={denyReason}
                       onChange={(e) => setDenyReason(e.target.value)} />
              </div>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-primary" onClick={() => handleApproval('결재')}>결재</button>
              <button type="button" className="btn btn-danger" onClick={() => handleApproval('반려')}>반려</button>
            </div>
          </div>
        </div>
      </div>
  );
}

export default HQMainPanel


