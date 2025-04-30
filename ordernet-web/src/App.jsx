import './App.css'
import WebMain from "./layout/WebMain.jsx";
import {BrowserRouter, Route, Routes }  from "react-router-dom";
import Logis from "./Logistics/LogisStatus/Logis.jsx";
import Inventory from "./Logistics/LogisManage/Inventory.jsx";

function App() {

  return (
      <div>
        <WebMain />
          <BrowserRouter>
              <Routes>
                  <Route path="/logis" element={<Logis />} />
                  <Route path="/inv" element={<Inventory />} />
              </Routes>
          </BrowserRouter>
      </div>
  )
}

export default App
