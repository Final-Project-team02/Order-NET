import StatusOverView from "./DashBoard/StatusOverView.jsx";
import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import React from "react";
import UncheckedList from "./DashBoard/UncheckedList.jsx";
import MonthlySalesGraph from "./DashBoard/MonthlySalesGraph.jsx";


import HQBranchMap from "./DashBoard/HQBranchMap.jsx";
import HQNoticeList from "./DashBoard/HQNoticeList.jsx";

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

                    {/* 두 번째 줄 */}

                    {/*<div className="row mb-3">*/}
                    {/*    <div className="col-md-6">*/}
                    {/*        <div className="h-100">*/}
                    {/*            <UncheckedList />*/}
                    {/*        </div>*/}
                    {/*    </div>*/}
                    {/*    <div className="col-md-6">*/}
                    {/*        <div className="h-100">*/}
                    {/*            <MonthlySalesGraph />*/}
                    {/*        </div>*/}
                    {/*    </div>*/}
                    {/*</div>*/}

                    <div className="row mb-3">
                        <div className="col-md-6 d-flex">
                            <div className="card flex-fill">
                                <UncheckedList/>
                            </div>
                        </div>
                        <div className="col-md-6 d-flex">
                            <div className="card flex-fill">
                                <MonthlySalesGraph/>
                            </div>
                        </div>
                    </div>

                    {/* 세 번째 줄 */}


                    <div className="row mb-3" >
                        <div className="col-md-6 d-flex">
                            <div className="card flex-fill" >
                                <HQNoticeList/>
                            </div>
                        </div>
                        <div className="col-md-6 d-flex">
                            <div className="card flex-fill">
                                <HQBranchMap/>
                            </div>
                        </div>
                </div>
            </div>
        </div>
</div>
)
    ;
}

export default HQOverall
