// HQUpdatePopUp.jsx
import React, { useState, useEffect } from 'react';
import DaumPostcode from 'react-daum-postcode';
import axios from "axios";

function HQUpdatePopUp({ isOpen, onClose,selectedBranchId,refreshClientList}) {
  console.log("팝업 렌더링됨, isOpen 상태:", isOpen);
  const [localIsOpen, setLocalIsOpen] = useState(isOpen); // 내부 상태로 관리
  const [isPostOpen, setIsPostOpen] = useState(false);
  const [formData, setFormData] = useState({
    branchName: '',
    branchSupervisor: '',
    userId: '',
    userPw: '',
    branchPhone: '',
    branchZipCode: '',
    branchRoadAddr: '',
    branchDetailAddr: '',
  });

  // isOpen 상태가 변경될 때만 localIsOpen 상태 업데이트
  useEffect(() => {
    setLocalIsOpen(isOpen); // 부모 컴포넌트로부터 받은 isOpen 상태 반영
  }, [isOpen]);

  // isOpen이 true 될 때 기존 데이터 세팅
  useEffect(() => {
    if (localIsOpen && selectedBranchId) {
      console.log("수정할 대리점 ID:", selectedBranchId);
      // 서버에서 대리점 정보 불러오기
      axios.get(`http://localhost:8080/HQMain/clientupdate/${selectedBranchId}`)
          .then(res => {
            const data = res.data;
            setFormData({
              branchName: data.branchName || '',
              branchSupervisor: data.branchSupervisor || '',
              userId: data.userId || '',
              userPw: data.userPw || '', // 초기화
              branchPhone: data.branchPhone || '',
              branchZipCode: data.branchZipCode || '',
              branchRoadAddr: data.branchRoadAddr || '',
              branchDetailAddr: data.branchDetailAddr || '',
            });
          })
          .catch(err => {
            console.error('대리점 정보 불러오기 실패:', err);
          });
    }
  }, [localIsOpen, selectedBranchId]);

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

  const handleSubmit = async () => {
    const payload = {
      branchId: selectedBranchId, // 대리점 ID 추가
      userId: formData.userId, // 수정할 대리점 ID
      userPw: formData.userPw, // 변경 비밀번호
      branchName: formData.branchName,
      branchSupervisor: formData.branchSupervisor,
      branchPhone: formData.branchPhone,
      branchZipCode: formData.branchZipCode,
      branchRoadAddr: formData.branchRoadAddr,
      branchDetailAddr: formData.branchDetailAddr,
    };

    try {
      const res = await axios.put('http://localhost:8080/HQMain/clientupdate', payload);
      alert(res.data);
      onClose();
      refreshClientList();
    } catch (err) {
      console.error('수정 실패:', err);
      alert('수정 실패');
    }
  };


  const handleClose = () => {
    setIsPostOpen(false);
    setLocalIsOpen(false); // 로컬 상태 변경
    onClose(); // 부모에게 팝업 닫혔음을 알림
  };

  if (!localIsOpen) return null; // 열리지 않으면 렌더 X

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
                      <input type="text" name="branchSupervisor" value={formData.branchSupervisor} onChange={handleChange} className="form-control" placeholder="대표자명" />
                    </div>
                  </div>

                  <div className="row mb-3">
                    <div className="col">
                      <input type="text" autoComplete="username" name="userId" value={formData.userId} onChange={handleChange} className="form-control" placeholder="대리점 ID" />
                    </div>
                    <div className="col">
                      <input type="password" autoComplete="current-password"  name="userPw" value={formData.userPw} onChange={handleChange} className="form-control" placeholder="비밀번호" />
                    </div>
                  </div>

                  <div className="mb-3">
                    <input type="text" name="branchPhone" value={formData.branchPhone} onChange={handleChange} className="form-control" placeholder="전화번호" />
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
