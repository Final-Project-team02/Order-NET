import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import HQSelectPanel from "./HQSelectPanel.jsx";
import HQMainPanel from "./HQMainPanel.jsx";
import HQPaymentCheck from "./HQPaymentCheck.jsx";
import Title from "../layout/Title.jsx";
import React from "react";



function HQMain() {
    const menuItems = [
        { text: "주문 확정", link: "/HQMain" },
        { text: "대리점 관리", link: "/HQClientList" },
        { text: "재고현황", link: "/HQStockStatus" }
    ];

    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="주문 확정"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
                    <Title breadcrumb= "☆ 주문 확정 > 주문 내역" panelTitle="주문내역"/>
                    <br/>
                    <HQSelectPanel/>
                    <HQMainPanel/>
                    <HQPaymentCheck/>
                </div>
            </div>
        </div>
    );
}

export default HQMain;