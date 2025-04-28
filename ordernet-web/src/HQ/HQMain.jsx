import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import HQSelectPanel from "./HQSelectPanel.jsx";
import HQMainPanel from "./HQMainPanel.jsx";
import HQPaymentCheck from "./HQPaymentCheck.jsx";


function HQMain() {
    const menuItems = ["주문 확정", "대리점 관리", "재고현황"];

    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="주문 확정"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
                    <HQSelectPanel/>
                    <HQMainPanel/>
                    <HQPaymentCheck/>
                </div>
            </div>
        </div>
    );
}

export default HQMain;