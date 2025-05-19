import React, { useEffect, useState } from 'react';

const UncheckedList = () => {
    const [rows, setRows] = useState([]);
    const [selectedOrderId, setSelectedOrderId] = useState(null);

    // 페이징 관련 상태
    const [currentPage, setCurrentPage] = useState(1);
    const rowsPerPage = 5;

    useEffect(() => {
        fetch('http://localhost:8080/HQMain/order-item-info')
            .then((response) => response.json())
            .then((data) => {
                const list = Array.isArray(data) ? data : [data];
                setRows(list);
            })
            .catch((error) => {
                console.error('데이터 불러오기 실패:', error);
            });
    }, []);

    const handleRowClick = (orderId) => {
        setSelectedOrderId((prev) => (prev === orderId ? null : orderId));
    };

    // 현재 페이지에 보여줄 행들 계산
    const indexOfLastRow = currentPage * rowsPerPage;
    const indexOfFirstRow = indexOfLastRow - rowsPerPage;
    const currentRows = rows.slice(indexOfFirstRow, indexOfLastRow);

    // 페이지 번호 계산
    const totalPages = Math.ceil(rows.length / rowsPerPage);

    const handlePageChange = (pageNum) => {
        if (pageNum < 1 || pageNum > totalPages) return;
        setCurrentPage(pageNum);
    };

    // 페이지네이션 숫자 표시용 함수
    const getPaginationNumbers = () => {
        const pageNumbers = [];
        if (totalPages <= 7) {
            for (let i = 1; i <= totalPages; i++) pageNumbers.push(i);
        } else {
            if (currentPage <= 4) {
                pageNumbers.push(1, 2, 3, 4, 5, '...', totalPages);
            } else if (currentPage >= totalPages - 3) {
                pageNumbers.push(1, '...', totalPages - 4, totalPages - 3, totalPages - 2, totalPages - 1, totalPages);
            } else {
                pageNumbers.push(
                    1,
                    '...',
                    currentPage - 1,
                    currentPage,
                    currentPage + 1,
                    '...',
                    totalPages
                );
            }
        }
        return pageNumbers;
    };

    return (
        <div className="p-4 mt-3 w-100" style={{ flexGrow: 1, display: "flex", flexDirection: "column" }}>
            {/* 여기 상위 박스에 그림자와 둥근 모서리 줌 */}
            <div
                style={{
                    padding: '1.5rem',
                    background: '#fdfdfd',
                    borderRadius: '12px',
                    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.06)',
                }}
            >
                <h2 className="h5 fw-bold mb-3" style={{
                    textAlign: 'center',
                    marginBottom: '1rem',
                    fontWeight: '600',
                    fontSize: '1.2rem',
                    color: '#333'
                }}>미결제 리스트</h2>

                {/* 테이블  영역 */}
                <div style={{ height: 300, overflowY: 'auto', width: '100%', margin: 0, padding: 0 }}>
                    {/* 헤더 테이블 */}
                    <table
                        className="table table-bordered"
                        style={{
                            tableLayout: 'fixed',
                            width: '100%',
                            marginBottom: 0,
                            borderCollapse: 'collapse',
                            boxSizing: 'border-box',
                        }}
                    >
                        <colgroup>
                            <col style={{ width: '160px' }} />
                            <col style={{ width: '130px' }} />
                            <col style={{ width: '130px' }} />
                            <col style={{ width: '130px' }} />
                        </colgroup>
                        <thead className="table-info">
                        <tr>
                            <th className="text-center" rowSpan="2">
                                주문번호
                            </th>
                            <th className="text-center" rowSpan="2">
                                대리점 ID
                            </th>
                            <th className="text-center" rowSpan="2">
                                주문일자
                            </th>
                            <th className="text-center" rowSpan="2">
                                주문현황
                            </th>
                        </tr>
                        </thead>
                    </table>

                    {/* 바디 테이블 */}
                    <table
                        className="table table-bordered"
                        style={{
                            tableLayout: 'fixed',
                            width: '100%',
                            marginBottom: 0,
                            borderCollapse: 'collapse',
                            boxSizing: 'border-box',
                        }}
                    >
                        <colgroup>
                            <col style={{ width: '160px' }} />
                            <col style={{ width: '130px' }} />
                            <col style={{ width: '130px' }} />
                            <col style={{ width: '130px' }} />
                        </colgroup>
                        <tbody>
                        {rows.length === 0 ? (
                            <tr>
                                <td colSpan="4" className="text-center">
                                    미결제 리스트가 없습니다.
                                </td>
                            </tr>
                        ) : (
                            currentRows.map((row, index) => (
                                <tr
                                    key={index}
                                    onClick={() => handleRowClick(row.orderId)}
                                    style={{
                                        cursor: 'pointer',
                                        backgroundColor: 'white',
                                        boxShadow:
                                            selectedOrderId === row.orderId ? '0 4px 8px rgba(0, 0, 0, 0.15)' : 'none',
                                        borderRadius: selectedOrderId === row.orderId ? '8px' : '0',
                                        transition: 'box-shadow 0.3s ease',
                                    }}
                                >
                                    <td className="text-center align-middle">{row.orderId}</td>
                                    <td className="text-center align-middle">{row.branchId}</td>
                                    <td className="text-center align-middle">{row.orderDate}</td>
                                    <td
                                        className="text-center align-middle"
                                        style={{ color: row.orderStatus === '반려' ? 'red' : 'black' }}
                                    >
                                        {row.orderStatus}
                                    </td>
                                </tr>
                            ))
                        )}
                        </tbody>
                    </table>
                </div>

                {/* 페이지네이션 */}
                {rows.length > rowsPerPage && (
                    <nav aria-label="Page navigation example" style={{ margin: 0, padding: 0 }}>
                        <ul className="pagination justify-content-center">
                            <li
                                className={`page-item ${currentPage === 1 ? 'disabled' : ''}`}
                                onClick={() => handlePageChange(currentPage - 1)}
                                style={{ cursor: currentPage === 1 ? 'default' : 'pointer' }}
                            >
                                <span className="page-link">이전</span>
                            </li>

                            {getPaginationNumbers().map((num, idx) =>
                                num === '...' ? (
                                    <li key={`dots-${idx}`} className="page-item disabled">
                                        <span className="page-link">...</span>
                                    </li>
                                ) : (
                                    <li
                                        key={num}
                                        className={`page-item ${currentPage === num ? 'active' : ''}`}
                                        onClick={() => handlePageChange(num)}
                                        style={{ cursor: 'pointer' }}
                                    >
                                        <span className="page-link">{num}</span>
                                    </li>
                                )
                            )}

                            <li
                                className={`page-item ${currentPage === totalPages ? 'disabled' : ''}`}
                                onClick={() => handlePageChange(currentPage + 1)}
                                style={{ cursor: currentPage === totalPages ? 'default' : 'pointer' }}
                            >
                                <span className="page-link">다음</span>
                            </li>
                        </ul>
                    </nav>
                )}
            </div>
        </div>
    );
};

export default UncheckedList;
