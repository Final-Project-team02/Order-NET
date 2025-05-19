// NoticeModal.jsx
import React, { useState } from 'react';
import './NoticeModal.css';


function getUserIdFromToken() {
    const token = localStorage.getItem("token");
    if (!token) return null;

    try {
        const payloadBase64 = token.split('.')[1];
        const decodedPayload = JSON.parse(atob(payloadBase64));
        return decodedPayload.sub;
    } catch (e) {
        console.error("JWT 디코딩 오류:", e);
        return null;
    }
}

function NoticeModal({ onClose, onSave }) {
    const [noticeTitle, setNoticeTitle] = useState('');
    const [noticeContent, setNoticeContent] = useState('');

    const handleSave = () => {
        if (!noticeTitle.trim()) {
            alert("제목을 입력해주세요.");
            return;
        }

        const userId = getUserIdFromToken();

        onSave({ noticeTitle, noticeContent, userId });
        onClose();
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">

                <div className="modal-header">
                    <h3 className="modal-title">공지 작성</h3>
                    <button className="modal-x-button" onClick={onClose}>×</button>
                </div>

                <div className="modal-field">
                    <label>제목</label>
                    <input
                        type="text"
                        value={noticeTitle}
                        onChange={(e) => setNoticeTitle(e.target.value)}
                        placeholder="제목을 입력하세요"
                    />
                </div>
                <div className="modal-field">
                    <label>내용</label>
                    <textarea
                        value={noticeContent}
                        onChange={(e) => setNoticeContent(e.target.value)}
                        placeholder="내용을 입력하세요"
                        rows={6}
                    />
                </div>
                <div className="modal-buttons">
                    <button type="button" className="btn-save" onClick={handleSave}>저장</button>
                    <button type="button" className="btn-close" onClick={onClose}>닫기</button>
                </div>
            </div>
        </div>
    );
}

export default NoticeModal;
