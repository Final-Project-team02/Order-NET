import { Home, LogOut } from "lucide-react";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

function HQTopbar({ title }) {

    const navigate = useNavigate();

    const [warehouseName, setWarehouseName] = useState("");

    useEffect(() => {
        const userRefId = localStorage.getItem("userRefId")
        const name = localStorage.getItem("warehouseName");
        setWarehouseName(name);
        console.log("지점장 이름:", userRefId);
        console.log("지점장 이름2:", name);
    }, []);

    const handleLogout = () => {
        // 저장된 토큰 제거
        localStorage.removeItem("token");
        localStorage.removeItem("userType");
        localStorage.removeItem("userRefId");
        localStorage.removeItem("warehouseName");

        // 로그인 페이지로 이동
        navigate("/login");
    };

  return (
      <nav className="navbar navbar-light justify-content-between px-4 border-bottom"
           style={{height: "60px", backgroundColor: '#343a40'}}>
            <span
                className="px-3 py-1 fw-bold"
                style={{
                  backgroundColor: '#ffffff',
                  color: '#333',
                  borderTopLeftRadius: '5px',
                  borderTopRightRadius: '5px',
                  border: '1px solid white',
                  height: "42px",
                  fontSize: '14px',
                  display: 'inline-block',
                  borderBottom: 'none',
                  marginTop: '15px'
                }}
            >
              {title}
            </span>

        <div className="d-flex align-items-center gap-3">
          <span className="me-2 text-white" style={{cursor: "pointer"}} onClick={handleLogout}>LOGOUT</span>
          <span className="fw-bold text-white">{warehouseName ? `${warehouseName}` : "본사"}>홍길동 님</span>
          <Home className="me-2 cursor-pointer text-white"/>
          <LogOut className="cursor-pointer text-white"/>
        </div>
      </nav>
  );
}

export default HQTopbar;

