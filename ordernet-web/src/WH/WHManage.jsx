import Title from "../layout/Title.jsx";
import WHSelectPanel from "./WHSelectPanel.jsx";
import DeliveryTable from "./DeliveryTable.jsx";
import HQSidebarMenu from "../HQ/HQSidebarMenu.jsx";
import HQTopbar from "../HQ/HQTopbar.jsx";
import {useState} from "react";

function WHManage() {
    const menuItems = [
        { text: "물류 현황", link: "/WHMain" },
        { text: "출고관리", link: "/WHManage" }
    ];

    const breadcrumb = "☆ 물류 관리 > 출고관리"; // Change this as needed
    const panelTitle = "출고관리"; // Change this as needed

    // 1. 필터 상태를 상위 컴포넌트에서 관리
    const [filters, setFilters] = useState({});
    const [branchList, setBranchList] = useState([]);

    // 필터 변경시 호출되는 함수
    const handleSearch = (newFilters) => {
        setFilters(newFilters); // 필터 상태 업데이트
    };

    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems} />
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="출고관리" />
                <div className="p-3 overflow-auto" style={{ height: "calc(100vh - 120px)" }}>
                    <Title breadcrumb={breadcrumb} panelTitle={panelTitle} />
                    <WHSelectPanel branchOptions={branchList} onSearch={handleSearch} />
                    <DeliveryTable filters={filters} setBranchList={setBranchList} />
                </div>
            </div>
        </div>
    );
}

export default WHManage;





