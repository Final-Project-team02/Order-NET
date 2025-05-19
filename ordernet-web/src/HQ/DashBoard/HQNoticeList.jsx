import React, {useEffect, useState} from "react";
import './NoticeListWidget.css';
import axios from "axios";
import NoticeModal from "./NoticeModal.jsx";
import NoticeDetailModal from "./NoticeDetailModal.jsx";


function HQNoticeList() {
    const [noticeData, setNoticeData] = useState([]);
    const [noticeWritePopup, setNoticeWritePopup] = useState(false);
    const [selectedNotice, setSelectedNotice] = useState(null);

    // 공지 조회
    const getNoticeList = () => {
        axios .get('http://localhost:8080/HQDashBoard/HQNotice')
            .then(res => {
                console.log("서버에서 받은 데이터:", res.data);
                setNoticeData(res.data); // 데이터를 반환
            })
            .catch(err => {
                console.error("에러 발생:", err);
            });
    }

    // 글 작성 버튼
    const handleCreate = () => {
        setNoticeWritePopup(true);
    };

    // 공지 작성
    const handleSave = (newNotice) => {
        axios.post('http://localhost:8080/HQDashBoard/HQNotice', newNotice)
            .then(() => {
                getNoticeList();
            })
            .catch(err => console.error("저장 오류:", err));
    };

    // 새로고침
    const handleRefresh = () => {
        getNoticeList(); // 기존 공지 불러오기 함수 재사용
    };

    // 공지 삭제
    const handleDelete = () => {
        if (!selectedNotice) return;

        const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
        if (!confirmDelete) return;

        axios.delete(`http://localhost:8080/HQDashBoard/HQNotice/${selectedNotice.noticeId}`)
            .then(() => {
                alert("삭제되었습니다.");
                setSelectedNotice(null); // 모달 닫기
                getNoticeList();         // 목록 새로고침
            })
            .catch((err) => {
                alert("삭제 중 오류가 발생했습니다.");
                console.error(err);
            });
    };



    useEffect(() => {
        getNoticeList();
    }, []);

    return (
        // notice-widget
        <div className="p-4 mt-3 w-100" style={{ width: '600px'}}>
            <div className="notice-header">
                <h3>공지사항</h3>
                <div className="notice-buttons">
                    <button onClick={handleCreate} className="btn-icon">
                        <img src="/pen.png" alt="글 작성" className="icon" />
                    </button>
                    <button onClick={handleRefresh} className="btn-icon">
                        <img src="/reload.png" alt="새로고침" className="icon" />
                    </button>
                </div>
            </div>
            {noticeData.length === 0 ? (
                <div className="no-notice">공지사항이 없습니다.</div>
            ) : (
                <div className="notice-table-wrapper">
                <table className="notice-table">
                    <thead>
                    <tr>
                        <th>제목</th>
                        <th>작성일</th>
                        <th>작성자</th>
                    </tr>
                    </thead>
                    <tbody>
                    {noticeData.map((notice,index) => (
                        <tr key={notice.noticeId} className="notice-row"
                            onClick={() => setSelectedNotice(notice)}>
                            <td className="title-cell">
                                {index < 3 ? (
                                    <span className="badge-notice">공지</span>
                                ) : null}
                                <span className="notice-title-text">{notice.noticeTitle}</span>
                            </td>
                            <td>{notice.noticeWriteDate}</td>
                            <td>{notice.userId}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                </div>
            )}
            {/* 공지 작성 팝업 */}
            {noticeWritePopup && (
                <NoticeModal
                    onClose={() => setNoticeWritePopup(false)}
                    onSave={handleSave}
                />
            )}
            {/* 공지 상세 팝업 */}
            {selectedNotice && (
                <NoticeDetailModal
                    notice={selectedNotice}
                    onClose={() => setSelectedNotice(null)}
                    onDelete={handleDelete}
                />
            )}

        </div>
    );
}

export default HQNoticeList
