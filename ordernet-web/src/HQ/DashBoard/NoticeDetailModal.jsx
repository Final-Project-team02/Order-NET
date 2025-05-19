import React from 'react';
import './NoticeModal.css'; // 기존 스타일 재사용

function NoticeDetailModal({ notice, onClose, onDelete }) {
    if (!notice) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content">

                <div className="modal-header">
                    <h3 className="modal-title">공지 상세</h3>

                    <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                        {/* 삭제 아이콘 버튼 */}
                        <button className="modal-delete-button" onClick={onDelete} title="삭제">
                            <img
                                src="/recycle-bin.png"
                                alt="삭제"
                                style={{ width: '20px', height: '20px', objectFit: 'contain' }}
                            />
                        </button>

                        {/* 닫기(X) 버튼 */}
                        <button className="modal-x-button" onClick={onClose}>×</button>
                    </div>
                </div>


                <div className="detail-body">
                    <div className="detail-row">
                        <div className="detail-label">제목</div>
                        <div className="detail-value">{notice.noticeTitle}</div>
                    </div>
                    <div className="detail-row">
                        <div className="detail-label">작성일</div>
                        <div className="detail-value">{notice.noticeWriteDate}</div>
                    </div>
                    <div className="detail-row">
                        <div className="detail-label">작성자</div>
                        <div className="detail-value">{notice.userId}</div>
                    </div>
                    <div className="detail-row">
                        <div className="detail-label">내용</div>
                        <div className="detail-value" style={{ whiteSpace: 'pre-wrap' }}>
                            {notice.noticeContent}
                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
}

export default NoticeDetailModal;
