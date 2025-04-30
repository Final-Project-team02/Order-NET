import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import HQSelectPanel from "./HQSelectPanel.jsx";
import HQMainPanel from "./HQMainPanel.jsx";
import HQPaymentCheck from "./HQPaymentCheck.jsx";
import HQClientList from "./HQClientList.jsx";
import HQStockStatus from "./HQStockStatus.jsx";


function HQMain() {
    const menuItems = [
        { text: "주문 확정", link: "/" },
        { text: "대리점 관리", link: "/" },
        { text: "재고현황", link: "/" }
    ];

    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="대리점 리스트"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
                    <HQSelectPanel/>
                    <HQMainPanel/>
                    <HQPaymentCheck/>
                    <HQClientList/>
                    <HQStockStatus/>
                </div>
            </div>
        </div>
    );
}

export default HQMain;