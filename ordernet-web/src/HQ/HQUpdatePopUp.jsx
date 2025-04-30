// HQUpdatePopUp.jsx
import React, { useState, useEffect } from 'react';
import DaumPostcode from 'react-daum-postcode';

function HQUpdatePopUp({ isOpen, onClose }) {
  const [isPostOpen, setIsPostOpen] = useState(false);
  const [formData, setFormData] = useState({
    branchName: '',
    supervisorName: '',
    branchId: '',
    password: '',
    phone: '',
    branchZipCode: '',
    branchRoadAddr: '',
    branchDetailAddr: '',
  });

  // 임시 데이터: 수정할 기존 정보
  const existingData = {
    branchName: 'ABC 대리점',
    supervisorName: '홍길동',
    branchId: 'abc123',
    phone: '010-1234-5678',
    branchZipCode: '12345',
    branchRoadAddr: '서울시 강남구 테헤란로 123',
    branchDetailAddr: '101동 202호',
  };

  // isOpen이 true 될 때 기존 데이터 세팅
  useEffect(() => {
    if (isOpen && existingData) {
      setFormData({
        branchName: existingData.branchName || '',
        supervisorName: existingData.supervisorName || '',
        branchId: existingData.branchId || '',
        password: '',
        phone: existingData.phone || '',
        branchZipCode: existingData.branchZipCode || '',
        branchRoadAddr: existingData.branchRoadAddr || '',
        branchDetailAddr: existingData.branchDetailAddr || '',
      });
    }
  }, [isOpen]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleComplete = (data) => {
    setFormData(prev => ({
      ...prev,
      branchZipCode: data.zonecode,  // DaumPostcode에서는 zonecode임!
      branchRoadAddr: data.address,
    }));
    setIsPostOpen(false);
  };

  const handleSubmit = () => {
    console.log('수정할 데이터:', formData);
    onClose();
  };

  const handleClose = () => {
    setIsPostOpen(false);
    onClose();
  };

  if (!isOpen) return null; // 열리지 않으면 렌더 X

  return (
      <>
        <div className="modal-backdrop fade show"
             onClick={(e) => { if (e.target === e.currentTarget) handleClose(); }}
        />

        <div className="modal fade show"
             tabIndex="-1"
             role="dialog"
             style={{
               display: 'block',
               position: 'fixed',
               top: '50%',
               left: '50%',
               zIndex: 1050,
               transform: 'translate(-50%, -50%)',
               height: 'auto',
             }}
             onClick={(e) => { if (e.target === e.currentTarget) handleClose(); }}
        >
          <div className="modal-dialog modal-fullscreen-md-down" role="document">
            <div className="modal-content">
              <div className="modal-header d-flex justify-content-center" style={{ backgroundColor: '#cfe2ff' }}>
                <h5 className="modal-title text-center"><b>대리점 수정</b></h5>
              </div>

              <div className="modal-body">
                <form>
                  <div className="row mb-3">
                    <div className="col">
                      <input type="text" name="branchName" value={formData.branchName} onChange={handleChange} className="form-control" placeholder="대리점명" />
                    </div>
                    <div className="col">
                      <input type="text" name="supervisorName" value={formData.supervisorName} onChange={handleChange} className="form-control" placeholder="대표자명" />
                    </div>
                  </div>

                  <div className="row mb-3">
                    <div className="col">
                      <input type="text" name="branchId" value={formData.branchId} onChange={handleChange} className="form-control" placeholder="대리점 ID" />
                    </div>
                    <div className="col">
                      <input type="password" name="password" value={formData.password} onChange={handleChange} className="form-control" placeholder="비밀번호" />
                    </div>
                  </div>

                  <div className="mb-3">
                    <input type="text" name="phone" value={formData.phone} onChange={handleChange} className="form-control" placeholder="전화번호" />
                  </div>

                  <div className="mb-3 d-flex align-items-center">
                    <label className="me-2">주소구분</label>
                    <div>
                      <input type="radio" id="road" name="branchRoadAddrType" checked readOnly />
                      <label htmlFor="road" className="ms-1">도로명</label>
                    </div>
                  </div>

                  <div className="d-flex mb-3">
                    <input
                        type="text"
                        className="form-control"
                        style={{ flex: 3 }}
                        placeholder="우편번호"
                        value={formData.branchZipCode}
                        readOnly
                    />
                    <button
                        type="button"
                        className="btn btn-secondary"
                        onClick={() => setIsPostOpen(true)}
                        style={{ flex: 0.3, backgroundColor: '#cfe2ff', color: 'black' }}
                    >
                      검색
                    </button>
                  </div>

                  <div className="mb-3">
                    <input type="text" className="form-control" placeholder="주소" value={formData.branchRoadAddr} readOnly />
                  </div>

                  <div className="mb-3">
                    <input type="text" name="branchDetailAddr" value={formData.branchDetailAddr} onChange={handleChange} className="form-control" placeholder="상세주소 (예: 101동 202호)" />
                  </div>
                </form>

                {isPostOpen && (
                    <div style={{
                      position: 'fixed',
                      top: '50%',
                      left: '50%',
                      transform: 'translate(-50%, -50%)',
                      zIndex: 1060,
                      backgroundColor: 'white',
                      padding: '20px',
                      borderRadius: '8px',
                      boxShadow: '0 0 10px rgba(0,0,0,0.25)',
                    }}>
                      <DaumPostcode onComplete={handleComplete} />
                      <button className="btn btn-outline-danger mt-2" onClick={() => setIsPostOpen(false)}>
                        닫기
                      </button>
                    </div>
                )}
              </div>

              <div className="modal-footer d-flex justify-content-center" style={{ borderTop: 'none' }}>
                <button type="button" className="btn btn-outline-danger me-2" onClick={handleClose}>취소</button>
                <button type="button" className="btn btn-outline-primary" onClick={handleSubmit}>수정</button>
              </div>

            </div>
          </div>
        </div>
      </>
  );
}

export default HQUpdatePopUp;
