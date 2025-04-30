import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import HQMain from "../HQ/HQMain.jsx";
import BranchMain from "../Client/BranchMain.jsx";
import HQInsertPopUp from "../HQ/HQInsertPopUp.jsx";
import HQPartInsertPopUp from "../HQ/HQPartInsertPopUp.jsx";
import HQRequestPopup from "../HQ/HQRequestPopup.jsx";
import HQUpdatePopUp from "../HQ/HQUpdatePopUp.jsx";
import HQStockStatus from "../HQ/HQStockStatus.jsx";
import HQClientList from "../HQ/HQClientList.jsx";

function WebMain() {
  return (
      <BrowserRouter>
        <Routes>
          {/* 처음 '/'로 들어오면 '/HQMain'으로 리디렉션 */}
          <Route path="/" element={<Navigate to="/HQMain" replace />} />
          <Route path={'/HQMain'}>
            <Route index element={<HQMain />} />
          </Route>
          <Route path="/BranchMain" element={<BranchMain/>} />
            <Route path="/HqPopup1" element={<HQInsertPopUp />} />
            <Route path="/HqPopup2" element={<HQPartInsertPopUp/>} />
            <Route path="/HqPopup3" element={<HQRequestPopup />} />
            <Route path="/HqPopup4" element={<HQUpdatePopUp />} />
            <Route path="/HQStockStatus" element={<HQStockStatus />} />
            <Route path="/HQCLientList" element={<HQClientList />} />
        </Routes>
      </BrowserRouter>
  );
}

export default WebMain