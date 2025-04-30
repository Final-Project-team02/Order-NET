
import Title from "../layout/Title.jsx";
import WHMainPanel from "./WHMainPanel.jsx";
import HQSidebarMenu from "../HQ/HQSidebarMenu.jsx";
import HQTopbar from "../HQ/HQTopbar.jsx";


function WHMain() {

    const menuItems = [
        { text: "물류 현황", link: "/WHMain" },
        { text: "물류 재고관리", link: "/WHManage" },
    ];


  return (
      <div className="d-flex vh-100">
        <HQSidebarMenu menuItems={menuItems}/>
        <div className="flex-grow-1 d-flex flex-column overflow-hidden">
          <HQTopbar title="물류현황"/>
          <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
           <Title breadcrumb= "☆ 물류 관리 > 물류 현황" panelTitle="물류 현황"/>
           <WHMainPanel/>
          </div>
        </div>
      </div>
  );
}


export default WHMain



