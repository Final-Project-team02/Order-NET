import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import HQMain from "../HQ/HQMain.jsx";

function WebMain() {
  return (
      <BrowserRouter>
        <Routes>
          {/* 처음 '/'로 들어오면 '/HQMain'으로 리디렉션 */}
          <Route path="/" element={<Navigate to="/HQMain" replace />} />

          <Route path={'/HQMain'}>
            <Route index element={<HQMain />} />
          </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default WebMain