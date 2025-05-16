import React, {useState} from "react";
import BranchTopbar from "./BranchTopbar.jsx";
import BranchSidebarMenu from "./BranchSidebarMenu.jsx";
import Title from "../layout/Title.jsx";
import Axios from "axios";
import {useParams} from "react-router-dom";

function BranchMain() {
    const menuItems = ["주문 내역"];
    const [orders, setOrders] = useState([]);
    const [selectedOrder, setSelectedOrder] = useState(null);
    const [partsData, setPartsData] = useState([]);
    const [orderNumber, setOrderNumber] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [noResults, setNoResults] = useState(false);
    const [hoveredOrderId, setHoveredOrderId] = useState(null);
    // 총 금액 계산
    const calculateTotalPrice = () => {
        return partsData.reduce((total, part) => {
            const price = parseFloat(part.partPrice) || 0;
            const quantity = parseInt(part.quantity) || 0;
            return total + price * quantity;
        }, 0).toLocaleString(); // 천 단위 쉼표 추가
    };

    const { agencyCode } = useParams();  // 주소에서 :agencyCode 파라미터 추출
    const branchId = agencyCode;         // 이 값을 실제 branchId로 사용


    // 나중에 변경해야하는
    // const branchId = "Busan01";

    const fetchOrders = async () => {
        if (startDate && endDate && startDate > endDate) {
            alert("시작 날짜는 종료 날짜보다 이전이어야 합니다.");
            return;
        }

        try {
            const params = {};

            if (orderNumber) params.orderId = orderNumber;
            if (startDate) params.startDate = startDate;
            if (endDate) params.endDate = endDate;
            console.log(params); // 확인용 로그
            const response = await Axios.get(`http://localhost:8080/branch/${branchId}/orders`, {
                params
            });

            const fetchedOrders = response.data;
            setOrders(fetchedOrders);
            setNoResults(fetchedOrders.length === 0);
            setSelectedOrder(null);
            setPartsData([]);
        } catch (error) {
            console.error("주문 목록을 불러오는 데 실패했습니다.", error);
        }
    };


    const handleOrderClick = async (orderId) => {
        setSelectedOrder(orderId);
        try {
            const response = await Axios.get(`http://localhost:8080/branch/order/${orderId}/parts`);
            setPartsData(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("부품 데이터를 불러오는 데 실패했습니다.", error);
        }
    };

    const selectedOrderInfo = orders.find(o => o.orderId === selectedOrder);


    return (
        <div className="d-flex vh-100">
            <BranchSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <BranchTopbar title="주문 내역"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
                    <Title breadcrumb="☆ 대리점 관리 > 주문 내역" panelTitle="주문내역"/>
                    <br/>
                    <div className="d-flex mb-4" style={{gap: "20px"}}>
                        <div style={titleBoxStyle}>주문 내역</div>
                        <div style={titleBoxStyle}>주문상세 내역</div>
                    </div>
                    <div className="d-flex" style={{gap: "20px", height: "calc(100vh - 250px)"}}>
                        {/* 주문 내역 */}
                        <div style={{...contentBoxStyle, display: "flex", flexDirection: "column"}}>
                            <div className="d-flex align-items-center mb-3 justify-content-between">
                                {/* 일자 관련 입력 */}
                                <div className="d-flex align-items-center" style={{gap: "10px", whiteSpace: "nowrap"}}>
                                    <span className="fw-bold"
                                          style={{marginRight: "15px", display: "inline"}}>일자</span>
                                    <input
                                        type="date"
                                        className="form-control"
                                        style={{minWidth: '100px'}}
                                        value={startDate}
                                        onChange={(e) => setStartDate(e.target.value)}
                                    />
                                    <span style={{marginLeft: '5px', marginRight: '5px'}}>~</span>
                                    <input
                                        type="date"
                                        className="form-control"
                                        style={{minWidth: '120px'}}
                                        value={endDate}
                                        onChange={(e) => setEndDate(e.target.value)}
                                    />
                                </div>

                                {/* 주문번호 입력 */}
                                <div className="d-flex align-items-center">
                                    <label htmlFor="orderNumber" className="form-label fw-bold me-2 mt-1">주문번호</label>
                                    <input
                                        id="orderNumber"
                                        type="text"
                                        className="form-control"
                                        placeholder="주문번호 입력"
                                        style={{width: "200px"}}
                                        value={orderNumber}
                                        onChange={(e) => setOrderNumber(e.target.value)}
                                    />
                                    <button style={searchButtonStyle} onClick={fetchOrders}>검색</button>
                                </div>
                            </div>

                            <div style={{overflowY: "auto", flex: 1}}>
                                <table className="table">
                                    <thead>
                                    <tr>
                                        <th style={headerCellStyle}>주문번호</th>
                                        <th style={headerCellStyle}>지점</th>
                                        <th style={headerCellStyle}>주문날짜</th>
                                        <th style={headerCellStyle}>주문금액(원)</th>
                                        <th style={headerCellStyle}>주문상태</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {orders.map((order, index) => (
                                        <tr
                                            key={index}
                                            onClick={() => handleOrderClick(order.orderId)}
                                            onMouseEnter={() => setHoveredOrderId(order.orderId)}
                                            onMouseLeave={() => setHoveredOrderId(null)}
                                            style={{
                                                cursor: "pointer",
                                                backgroundColor:
                                                    selectedOrder === order.orderId
                                                        ? "#E0F7FA"
                                                        : hoveredOrderId === order.orderId
                                                            ? "#f1faff"
                                                            : "white",
                                            }}
                                        >
                                            <td style={tdStyle}>{order.orderId}</td>
                                            <td style={tdStyle}>{order.branchId}</td>
                                            <td style={tdStyle}>{order.orderDate}</td>
                                            <td style={tdStyle}>{order.orderPrice.toLocaleString()} (원)</td>
                                            <td style={tdStyle}>{order.orderStatus}</td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                            {noResults && <div className="text-center">검색된 주문이 없습니다.</div>}
                        </div>


                        {/* 주문 상세 내역 */}
                        <div style={{...contentBoxStyle, display: "flex", flexDirection: "column"}}>
                            {selectedOrderInfo ? (
                                <>
                                    <div style={{marginBottom: "20px"}}>
                                        <div style={{
                                            ...orderInfoStyle,
                                            flexDirection: "row",
                                            justifyContent: "space-between",
                                            alignItems: "center"
                                        }}>
                                            <div>
                                                <h6 className="fw-bold m-0">주문 번호 : {selectedOrderInfo.orderId}</h6>
                                                <h6 className="fw-bold m-0">주문 날짜 : {selectedOrderInfo.orderDate}</h6>
                                            </div>
                                            <div style={{display: "flex", alignItems: "center"}}>
                                                <h6 className="fw-bold m-0 me-2">상태</h6>
                                                {selectedOrderInfo.orderStatus === "반려" ? (
                                                    <span style={statusBadgeStyle}>
    반려&nbsp;
                                                        <span style={{color: "red"}}>
      ({selectedOrderInfo.orderDeny ? selectedOrderInfo.orderDeny : "사유 미입력"})
    </span>
  </span>
                                                ) : (
                                                    <span
                                                        style={statusBadgeStyle}>{selectedOrderInfo.orderStatus}</span>
                                                )}

                                            </div>
                                        </div>
                                    </div>

                                    <div style={{flex: 1, overflowY: "auto", width: "100%"}}>
                                        <table className="table">
                                            <thead>
                                            <tr>
                                                <th style={headerCellStyle}>부품 번호</th>
                                                <th style={headerCellStyle}>부품 카테고리</th>
                                                <th style={headerCellStyle}>부품 명</th>
                                                <th style={headerCellStyle}>주문 수량(개)</th>
                                                <th style={headerCellStyle}>부품 총 가격</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {partsData.map((part, index) => (
                                                <tr key={index}>
                                                    <td>{part.partId}</td>
                                                    <td>{part.partCate}</td>
                                                    <td>{part.partName}</td>
                                                    <td>{part.quantity}</td>
                                                    <td>{(parseFloat(part.partPrice) * parseInt(part.quantity)).toLocaleString()} (원)</td>
                                                </tr>
                                            ))}
                                            </tbody>
                                        </table>
                                    </div>

                                    <div style={totalPriceBoxStyle}>
                                        <span>총 주문 금액</span>
                                        <span>{calculateTotalPrice()} (원)</span>
                                    </div>
                                </>
                            ) : (
                                <div className="text-center my-auto">주문을 선택하세요.</div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

// 스타일
const titleBoxStyle = {
    flex: 1,
    backgroundColor: "#CFE2FF",
    padding: "20px",
    borderRadius: "8px",
    boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
    fontSize: "18px",
    fontWeight: "bold",
    display: "flex",
    alignItems: "center",
    justifyContent: "center"
};

const contentBoxStyle = {
    flex: 1,
    backgroundColor: "#ffffff",
    padding: "15px",
    borderRadius: "8px",
    boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
    minHeight: "0"
};

const searchButtonStyle = {
    padding: "5px 10px",
    borderRadius: "5px",
    fontWeight: "bold",
    marginLeft: "10px",
    backgroundColor: "#CFE2FF",
    border: "none"
};

const headerCellStyle = {
    backgroundColor: "#E3F0FF"
};

const orderInfoStyle = {
    padding: "10px",
    display: "flex",
    flexDirection: "column"
};

const statusBadgeStyle = {
    fontWeight: "bold",
    color: "black",
    backgroundColor: "#D9D9D9",
    padding: "2px 8px",
    borderRadius: "5px",
    fontSize: "12px"
};

const totalPriceBoxStyle = {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "10px 15px",
    backgroundColor: "#F0F8FF",
    borderRadius: "8px",
    fontWeight: "bold",
    fontSize: "16px",
    marginTop: "10px"
};

const tdStyle = {backgroundColor: "inherit"};

export default BranchMain;
