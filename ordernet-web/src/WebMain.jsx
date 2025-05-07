import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import HQMain from "./HQ/HQMain.jsx";
import BranchMain from "./Branch/BranchMain.jsx";
import HQStockStatus from "./HQ/HQStockStatus.jsx";
import HQClientList from "./HQ/HQClientList.jsx";
import WHManage from "./WH/WHManage.jsx";
import WHMain from "./WH/WHMain.jsx";

function WebMain() {
    return (
        <BrowserRouter>
            <Routes>
                {/* 처음 '/'로 들어오면 '/HQMain'으로 리디렉션 */}
                <Route path="/" element={<Navigate to="/HQMain" replace/>}/>
                <Route path={"/HQMain"}>
                    <Route index element={<HQMain/>}/>
                </Route>
                <Route path={"/HQClientList"} element={<HQClientList/>}></Route>
                <Route path={"/HQStockStatus"} element={<HQStockStatus/>}></Route>
                <Route path={"/BranchMain"} element={<BranchMain/>}/>
                <Route path={"/WHMain"} element={<WHMain/>}/>
                <Route path={"/WHManage"} element={<WHManage/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default WebMain