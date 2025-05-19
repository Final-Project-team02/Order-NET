import React, {useState} from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css'; // npm install bootstrap-icons 필요
import '../login.css';
import axios from "axios";
import {useNavigate} from "react-router-dom";

function LoginForm() {

    const [selectedTab, setSelectedTab] = useState("Head office");
    const [userId, setUserId] = useState("");
    const [userPw, setUserPw] = useState("");
    const navigate = useNavigate();

    const tabs = ["Head office", "Dealer Portal", "Ware house"];

    const handleLogin = async () => {
        // userType 변환
        let userType;
        if (selectedTab === "Head office") userType = "본사";
        else if (selectedTab === "Dealer Portal") userType = "대리점";
        else if (selectedTab === "Ware house") userType = "물류센터";

        try {
            const response = await axios.post("http://localhost:8080/login", {

                userId,
                userPw,
                userType,
            });

            // const { token, userType, userRefId } = response.data;
            const { token, userType: resUserType, userRefId, branchSupervisor, warehouseName } = response.data;

            // 토큰 저장
            localStorage.setItem("token", token);
            localStorage.setItem("userType", resUserType);
            localStorage.setItem("userRefId", userRefId);
            if (branchSupervisor) {
                localStorage.setItem("branchSupervisor", branchSupervisor);
            }
            if (warehouseName) {
                localStorage.setItem("warehouseName", warehouseName);
            }

            // userType에 따른 페이지 이동
            // if (userType === "본사") navigate("/HQMain");
            // else if (userType === "대리점") navigate("/BranchMain");
            // else if (userType === "물류센터") navigate("/WHMain");
            if (resUserType === "본사") navigate("/HQOverall");
            else if (resUserType === "대리점") navigate(`/BranchMain/${userRefId}`);
            else if (resUserType === "물류센터")  navigate(`/WHMain/${encodeURIComponent(userRefId)}`); /*navigate(`/WHMain/${userRefId}`); */
        } catch (error) {
            console.error("로그인 실패:", error);
            alert("로그인 실패: 아이디, 비밀번호 또는 권한이 올바르지 않습니다.");
        }
    };


    return (
        <div
            className="d-flex justify-content-center align-items-center vh-100"
            style={{
                background: 'linear-gradient(to bottom right, #3e3e3e, #1f1f1f)',
            }}
        >
            <div
                className="p-5"
                style={{
                    width: '550px',
                    height: '500px',
                    backgroundColor: 'rgba(255, 255, 255, 0.05)',
                    borderRadius: '12px',
                    boxShadow: '0 0 15px rgba(255, 255, 255, 0.1)',
                    backdropFilter: 'blur(10px)',
                    border: '1px solid rgba(255, 255, 255, 0.1)',
                }}
            >
                {/* 프로필 아이콘 */}
                <div className="text-center mb-4">
                    <div
                        style={{
                            width: '150px',
                            height: '150px',
                            borderRadius: '50%',
                            backgroundColor: 'rgba(255,255,255,0.1)',
                            margin: '0 auto',
                        }}
                    >
                        <i
                            className="bi bi-person-fill"
                            style={{
                                fontSize: '98px',
                                color: 'rgba(255,255,255,0.4)',
                                lineHeight: '150px',
                            }}
                        ></i>
                    </div>
                </div>

                {/* 이메일 입력 - 아이콘 + 텍스트 placeholder */}
                <div className="input-group mb-3">
          <span
              className="input-group-text bg-transparent border-bottom"
              style={{
                  borderTop: 'none',
                  borderLeft: 'none',
                  borderRight: 'none',
                  color: 'rgba(255,255,255,0.5)',
                  borderRadius: '0',
              }}
          >
            <i className="bi bi-envelope-fill"></i>
          </span>
                    <input
                        type="text"
                        className="form-control bg-transparent text-white border-bottom"
                        placeholder="Email ID"
                        value={userId}
                        style={{
                            borderTop: 'none',
                            borderLeft: 'none',
                            borderRight: 'none',
                            borderRadius: '0',
                        }}
                        onChange={(e) => setUserId(e.target.value)}
                    />
                </div>

                {/* 비밀번호 입력 - 아이콘 + 텍스트 placeholder */}
                <div className="input-group mb-4">
          <span
              className="input-group-text bg-transparent border-bottom"
              style={{
                  borderTop: 'none',
                  borderLeft: 'none',
                  borderRight: 'none',
                  color: 'rgba(255,255,255,0.5)',
                  borderRadius: '0',
              }}
          >
            <i className="bi bi-lock-fill"></i>
          </span>
                    <input
                        type="password"
                        className="form-control bg-transparent text-white border-bottom"
                        value={userPw}
                        onChange={(e) => setUserPw(e.target.value)}
                        placeholder="Password"
                        style={{
                            borderTop: 'none',
                            borderLeft: 'none',
                            borderRight: 'none',
                            borderRadius: '0',
                        }}
                    />
                </div>

                {/* ⬇️ 여기에 탭 버튼 삽입 */}
                <div className="btn-group w-100 mb-4" role="group">
                    {tabs.map((tab) => (
                        <React.Fragment key={tab}>
                            <input
                                type="radio"
                                className="btn-check"
                                name="userType"
                                id={tab}
                                checked={selectedTab === tab}
                                onChange={() => setSelectedTab(tab)}
                            />
                            <label
                                className={`btn btn-outline-secondary ${selectedTab === tab ? "active" : ""}`}
                                htmlFor={tab}
                                style={{ fontWeight: "bold" }}
                            >
                                {tab}
                            </label>
                        </React.Fragment>
                    ))}
                </div>

                {/* 로그인 버튼 */}
                <button
                    type="submit"
                    className="btn w-100"
                    style={{
                        backgroundColor: '#5e5e5e',
                        color: 'white',
                        border: 'none',
                        fontWeight: 'bold',
                        letterSpacing: '1px',
                    }}
               onClick={handleLogin} >
                    LOGIN
                </button>
            </div>
        </div>
    );
}

export default LoginForm;

