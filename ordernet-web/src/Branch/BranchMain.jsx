import React, { useState } from "react";
import BranchTopbar from "./BranchTopbar.jsx";
import BranchSidebarMenu from "./BranchSidebarMenu.jsx";
import Title from "../layout/Title.jsx";

function BranchMain() {
    const menuItems = ["주문 내역"];

    const [orders] = useState([
        {
            orderNumber: "123456",
            orderDate: "2025-04-25",
            deliveryDate: "2025-04-30",
            orderAmount: "₩50,000",
            orderStatus: "출고 대기"
        },
        {
            orderNumber: "123457",
            orderDate: "2025-04-26",
            deliveryDate: "2025-05-01",
            orderAmount: "₩30,000",
            orderStatus: "승인 대기"
        },
        {
            orderNumber: "123458",
            orderDate: "2025-04-26",
            deliveryDate: "2025-05-01",
            orderAmount: "₩30,000",
            orderStatus: "출고"
        },
        {
            orderNumber: "123459",
            orderDate: "2025-04-26",
            deliveryDate: "2025-05-01",
            orderAmount: "₩30,000",
            orderStatus: "승인"
        }
    ]);

    const partsData = [
        {
            partNumber: "asvv101",
            category: "엔진",
            partName: "엔진-피스톨",
            quantity: 500,
            totalPrice: "₩30,000"
        },
        {
            partNumber: "asvv102",
            category: "엔진",
            partName: "엔진-밸브",
            quantity: 300,
            totalPrice: "₩15,000"
        }
    ];

    return (
        <div className="d-flex vh-100">
            <BranchSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <BranchTopbar title="주문 내역"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
                    <Title breadcrumb= "☆ 대리점 관리 > 주문 내역" panelTitle="주문내역"/>
                    <br/>

                    {/* 상단 제목 두 개 */}
                    <div className="d-flex mb-4" style={{gap: "20px"}}>
                        <div style={titleBoxStyle}>주문 내역</div>
                        <div style={titleBoxStyle}>주문상세 내역</div>
                    </div>

                    {/* 주문 내역과 주문 상세 내역을 2분할 */}
                    <div className="d-flex" style={{gap: "20px", height: "calc(100vh - 250px)"}}>

                        {/* 왼쪽: 주문 내역 */}
                        <div style={{...contentBoxStyle, display: "flex", flexDirection: "column"}}>
                            <div className="d-flex align-items-center mb-3" style={{justifyContent: "space-between"}}>
                                <div className="d-flex align-items-center">
                                    <label htmlFor="orderNumber" className="form-label" style={{fontWeight: "bold", marginRight: "10px", marginTop: "5px"}}>주문번호</label>
                                    <input id="orderNumber" type="text" className="form-control" placeholder="주문번호 입력" style={{width: "200px"}}/>
                                    <button style={searchButtonStyle}>검색</button>
                                </div>
                                <div>
                                    <button style={dateButtonStyle}>날짜순</button>
                                </div>
                            </div>

                            {/* 주문 테이블 */}
                            <div style={{overflowY: "auto", flex: 1}}>
                                <table className="table">
                                    <thead>
                                    <tr>
                                        <th style={headerCellStyle}>주문번호</th>
                                        <th style={headerCellStyle}>주문날짜</th>
                                        <th style={headerCellStyle}>납품일자</th>
                                        <th style={headerCellStyle}>주문금액(원)</th>
                                        <th style={headerCellStyle}>주문상태</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {orders.map((order, index) => (
                                        <tr key={index}>
                                            <td>{order.orderNumber}</td>
                                            <td>{order.orderDate}</td>
                                            <td>{order.deliveryDate}</td>
                                            <td>{order.orderAmount}</td>
                                            <td>{order.orderStatus}</td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        {/* 오른쪽: 주문 상세 내역 */}
                        <div style={{...contentBoxStyle, display: "flex", flexDirection: "column"}}>

                            {/* 상단 주문 정보 */}
                            <div style={{marginBottom: "20px"}}>
                                <div style={{
                                    ...orderInfoStyle,
                                    flexDirection: "row",
                                    justifyContent: "space-between",
                                    alignItems: "center"
                                }}>
                                    {/* 왼쪽: 주문번호 & 주문날짜 */}
                                    <div style={{display: "flex", flexDirection: "column", alignItems: "flex-start"}}>
                                        <div style={{marginBottom: "8px"}}>
                                            <h6 style={{fontWeight: "bold", margin: 0}}>주문 번호 : 123456</h6>
                                        </div>
                                        <div>
                                            <h6 style={{fontWeight: "bold", margin: 0}}>주문 날짜 : 2025-04-25</h6>
                                        </div>
                                    </div>

                                    {/* 오른쪽: 상태 & 납품날짜 */}
                                    <div style={{display: "flex", flexDirection: "column", alignItems: "flex-start"}}>
                                        <div style={{marginBottom: "8px", display: "flex", alignItems: "center"}}>
                                            <h6 style={{fontWeight: "bold", margin: 0, marginRight: "5px"}}>상태</h6>
                                            <span style={statusBadgeStyle}>반려</span>
                                            <span style={{color: "red", marginLeft: "10px"}}>물류센터 재고 부족</span>
                                        </div>
                                        <div>
                                            <h6 style={{fontWeight: "bold", margin: 0}}>
                                                납품 날짜 : 2025-04-30
                                            </h6>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            {/* 상품 목록 테이블 */}
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
                                            <td>{part.partNumber}</td>
                                            <td>{part.category}</td>
                                            <td>{part.partName}</td>
                                            <td>{part.quantity}</td>
                                            <td>{part.totalPrice}</td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>


                            {/* 총 주문 금액 (항상 맨 밑에) */}
                            <div style={totalPriceBoxStyle}>
                                <span>총 주문 금액</span>
                                <span>₩45,000</span>
                            </div>

                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
}

// 공통 스타일 객체
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

const dateButtonStyle = {
    padding: "5px 10px",
    borderRadius: "5px",
    fontWeight: "bold",
    backgroundColor: "#D9D9D9",
    border: "none"
};

const headerCellStyle = {
    backgroundColor: "#E3F0FF"
};

const orderInfoStyle = {
    flex: 1,
    padding: "10px",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    textAlign: "center",
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
    marginTop: "20px",
    fontWeight: "bold",
    fontSize: "18px",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center"
};

export default BranchMain;
