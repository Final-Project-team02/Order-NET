import StatusOverView from "./DashBoard/StatusOverView.jsx";
import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import React from "react";
import UncheckedList from "./DashBoard/UncheckedList.jsx";
import MonthlySalesGraph from "./DashBoard/MonthlySalesGraph.jsx";


function HQOverall() {
    const menuItems = [
        {text: "대쉬 보드", link: "/HQOverall"},
        {text: "주문 확정", link: "/HQMain"},
        {text: "대리점 관리", link: "/HQClientList"},
        {text: "재고현황", link: "/HQStockStatus"}
    ];

    return (

        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="주문 확정"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
                    <br/>
                    {/* 본사 주문 현황 */}
                    <StatusOverView/>
                    <br/>
                    <div className="row d-flex" style={{ height: "300px" }}>
                        <div className="col-md-6 mb-3 d-flex flex-column" style={{ height: '100%' }}>
                            <UncheckedList style={{ flex: 1 }} />
                        </div>
                        <div className="col-md-6 mb-3 d-flex flex-column justify-content-center" style={{ height: '200%' }}>
                            <MonthlySalesGraph style={{ flex: 1 }} />
                        </div>
                    </div>

                </div>

            </div>
        </div>
    );
}

export default HQOverall
