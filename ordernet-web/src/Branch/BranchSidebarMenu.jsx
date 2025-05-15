import folder from "../assets/folder.png";
import {useEffect, useState} from "react";
import axios from "axios";

function BranchSidebarMenu({menuItems}) {
  // 서버 시간 받아오기
  const [time, setTime] = useState({ date: '', time: '' });

  // 서버 시간 주기적으로 가져오기
  useEffect(() => {
    const interval = setInterval(() => {
      // axios를 사용해 서버 시간 요청
      axios.get("http://localhost:8080/HQMain/server-time")
          .then((response) => {
            const serverTime = new Date(response.data.time); // 서버에서 받은 시간
            // 날짜 문자열: YYYY-MM-DD
            const dateStr = serverTime.toISOString().split('T')[0];


            // 시간 문자열: HH:MM:SS (한국 시간 기준)
            const timeStr = serverTime.toLocaleTimeString("ko-KR", {
              hour12: false,
              hour: "2-digit",
              minute: "2-digit",
              second: "2-digit",
              timeZone: "Asia/Seoul",
            });

            setTime({ date: dateStr, time: timeStr });
          })
          .catch((error) => {
            console.error("서버 시간 매칭 오류 :", error);
          });
    }, 1000) ;// 1초마다 갱신

    return () => clearInterval(interval); // 언마운트 시 인터벌 제거
  }, []);

    return (
        <div className="d-flex flex-column text-white vh-100 p-3" style={{width: '250px', backgroundColor: '#343a40'}}>
            <div className="fs-1 fw-bold mb-4 border-bottom pb-2 text-center" style={{height: '75px', whiteSpace: 'nowrap'}}>Order Net</div>

          <div className="mb-4">
            <div className="small text-white text-center fs-2">{time.date  || "날짜 없음"}</div>
            <div className="fw-bold border-bottom text-center pb-4 fs-2">{time.time  || "시간 없음"}</div>
          </div>

            <ul className="nav nav-pills flex-column">
                {menuItems.map((item, index) => (
                    <li className="nav-item" key={index}>
                        <a href="#" className="nav-link text-white">
                            <img src={folder} style={{ width: '20px', height: '20px', marginRight: '8px' }}/>{item}
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default BranchSidebarMenu
