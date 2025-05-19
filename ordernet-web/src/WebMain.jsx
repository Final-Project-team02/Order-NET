import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import HQMain from "./HQ/HQMain.jsx";
import BranchMain from "./Branch/BranchMain.jsx";
import HQStockStatus from "./HQ/HQStockStatus.jsx";
import HQClientList from "./HQ/HQClientList.jsx";
import WHManage from "./WH/WHManage.jsx";
import WHMain from "./WH/WHMain.jsx";
import LoginForm from "./layout/LoginForm.jsx";
import PrivateRoute from "./layout/PrivateRoute.jsx";
import HQClientRanking from "./HQ/HQClientRanking.jsx";
import HQOverall from "./HQ/HQOverall.jsx";

function WebMain() {
    return (
        <BrowserRouter>
            <Routes>
                {/* 처음 '/'로 들어오면 '/HQMain'으로 리디렉션 */}
                <Route path="/" element={<Navigate to="/HQOverall" place/>}/>
                <Route path={"/HQMain"}>
                    <Route index element={
                        <PrivateRoute>
                            <HQMain />
                        </PrivateRoute>
                    }
                    />
                </Route>
                <Route path={"/HQClientList"} element={<HQClientList/>}></Route>
                <Route path={"/HQStockStatus"} element={<HQStockStatus/>}></Route>

                <Route path={"/BranchMain/:agencyCode"}>
                    <Route index element={

                        <PrivateRoute>
                            <BranchMain />
                        </PrivateRoute>
                    }
                    />
                </Route>

                <Route path={"/WHMain/:agencyCode"}>
                    <Route index element={
                        <PrivateRoute>
                            <WHMain />
                        </PrivateRoute>
                    }
                    />
                </Route>

                <Route path="/WHManage/:agencyCode" element={<PrivateRoute><WHManage/></PrivateRoute>}/>
                <Route path="/login" element={<LoginForm />} />
                <Route path={"/HQClientRanking"} element={<HQClientRanking/>}></Route>
                <Route path="/BranchMain/:agencyCode" element={<BranchMain />} />
                <Route path={"/WHMain"} element={<WHMain/>}/>
                <Route path={"/WHManage"} element={<WHManage/>}/>
                <Route path={"/HQOverall"} element={<HQOverall/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default WebMain