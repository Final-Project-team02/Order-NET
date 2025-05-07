
// WHManage.jsx

import Title from "../layout/Title.jsx";
import WHSelectPanel from "./WHSelectPanel.jsx";
import DeliveryTable from "./DeliveryTable.jsx";
import HQSidebarMenu from "../HQ/HQSidebarMenu.jsx";
import HQTopbar from "../HQ/HQTopbar.jsx";

function WHManage() {
    const menuItems = [
        { text: "물류 현황", link: "/WHMain" },
        { text: "물류 재고관리", link: "/WHManage" }
    ];

    const breadcrumb = "☆ 물류 관리 > 물류 재고관리"; // Change this as needed
    const panelTitle = "물류 재고관리"; // Change this as needed

    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems} />
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="물류 재고관리" />
                <div className="p-3 overflow-auto" style={{ height: "calc(100vh - 120px)" }}>
                    <Title breadcrumb={breadcrumb} panelTitle={panelTitle} />
                    <WHSelectPanel/>
                   <DeliveryTable/>
                </div>
            </div>
        </div>
    );
}

export default WHManage;


//     const menuItems = ["물류 현황", "물류 재고관리"];


