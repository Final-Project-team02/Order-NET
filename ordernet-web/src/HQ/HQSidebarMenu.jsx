import { Home, LogOut } from "lucide-react";
import { Link } from "react-router-dom";

function HQSidebarMenu({ menuItems }) {


    // 로그인된 사용자 정보 가져오기
    const userType = localStorage.getItem("userType");
    const userRefId = localStorage.getItem("userRefId");

    // 사용자 타입에 따른 메인 경로 설정
    let mainLink = "/";
    if (userType === "본사") mainLink = "/HQMain";
    else if (userType === "물류센터") mainLink = `/WHMain/${encodeURIComponent(userRefId)}`;

    return (
        <div className="d-flex flex-column text-white vh-100 p-3" style={{ width: '250px', backgroundColor: '#343a40' }}>
            {/* Order Net 클릭 시 메인으로 이동 */}
            <Link to={mainLink} style={{ textDecoration: 'none', color: 'white' }}>
                <div className="fs-1 fw-bold mb-4 border-bottom pb-2 text-center" style={{ height: '75px', whiteSpace: 'nowrap' }}>
                    Order Net
                </div>
            </Link>

            <div className="mb-4">
                <div className="small text-white text-center fs-2">2025.04.10</div>
                <div className="fw-bold border-bottom text-center pb-4 fs-2">12:24:05</div>
            </div>

            <ul className="nav nav-pills flex-column">
                {menuItems.map((item, index) => (
                    <li className="nav-item" key={index}>
                        <Link to={item.link} className="nav-link text-white">
                            📁 {item.text}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default HQSidebarMenu;

