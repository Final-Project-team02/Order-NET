import React, { useState } from 'react';
import DaumPostcode from 'react-daum-postcode';

function HQInsertPopUp() {
  const [isOpen, setIsOpen] = useState(false);
  const [isPostOpen, setIsPostOpen] = useState(false);
  const [formData, setFormData] = useState({
    branchName: '',
    ceoName: '',
    branchId: '',
    password: '',
    phoneNumber: '',
    zonecode: '',
    address: '',
    detailAddress: '',
  });

  const openPopup = () => {
    setIsOpen(true);
  };

  const closePopup = () => {
    setIsOpen(false);
    setIsPostOpen(false);
  };

  const handleComplete = (data) => {
    setFormData((prev) => ({
      ...prev,
      zonecode: data.zonecode,
      address: data.address,
    }));
    setIsPostOpen(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('등록할 대리점 정보:', formData);
    // 여기에 Axios POST 요청 추가 가능
  };

  return (
    <div>
      <button className="btn btn-primary mt-5" onClick={openPopup}>
        대리점 등록
      </button>

      {isOpen && (
        <>
          {/* 배경 블러 */}
          <div
            className="modal-backdrop fade show"
            onClick={(e) => {
              if (e.target === e.currentTarget) closePopup();
            }}
          />

          {/* 모달 */}
          <div
            className="modal fade show"
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
            onClick={(e) => {
              if (e.target === e.currentTarget) closePopup();
            }}
          >
            <div className="modal-dialog modal-fullscreen-md-down" role="document">
              <div className="modal-content">
                <div className="modal-header d-flex justify-content-center" style={{ backgroundColor: '#cfe2ff' }}>
                  <h5 className="modal-title text-center">
                    <b>대리점 등록</b>
                  </h5>
                </div>
                <div className="modal-body">
                  <form onSubmit={handleSubmit}>
                    <div className="row mb-3">
                      <div className="col">
                        <input
                          type="text"
                          name="branchName"
                          value={formData.branchName}
                          onChange={handleChange}
                          className="form-control"
                          placeholder="대리점명"
                        />
                      </div>
                      <div className="col">
                        <input
                          type="text"
                          name="ceoName"
                          value={formData.ceoName}
                          onChange={handleChange}
                          className="form-control"
                          placeholder="대표자명"
                        />
                      </div>
                    </div>

                    <div className="row mb-3">
                      <div className="col">
                        <input
                          type="text"
                          name="branchId"
                          value={formData.branchId}
                          onChange={handleChange}
                          className="form-control"
                          placeholder="대리점 ID"
                        />
                      </div>
                      <div className="col">
                        <input
                          type="password"
                          name="password"
                          value={formData.password}
                          onChange={handleChange}
                          className="form-control"
                          placeholder="비밀번호"
                        />
                      </div>
                    </div>

                    <div className="mb-3">
                      <input
                        type="text"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="전화번호"
                      />
                    </div>

                    <div className="mb-3 d-flex align-items-center">
                      <label className="me-2">주소구분</label>
                      <div>
                        <input type="radio" id="road" name="addressType" defaultChecked readOnly />
                        <label htmlFor="road" className="ms-1">도로명</label>
                      </div>
                    </div>

                    <div className="d-flex mb-3">
                      <input
                        type="text"
                        className="form-control"
                        style={{ flex: 3 }}
                        placeholder="우편번호"
                        value={formData.zonecode}
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
                      <input
                        type="text"
                        name="address"
                        className="form-control"
                        placeholder="주소"
                        value={formData.address}
                        readOnly
                      />
                    </div>

                    <div className="mb-3">
                      <input
                        type="text"
                        name="detailAddress"
                        className="form-control"
                        placeholder="상세주소 (예: 101동 202호)"
                        value={formData.detailAddress}
                        onChange={handleChange}
                      />
                    </div>

                    {/* 우편번호 검색 팝업 */}
                    {isPostOpen && (
                      <div
                        style={{
                          position: 'fixed',
                          top: '50%',
                          left: '50%',
                          transform: 'translate(-50%, -50%)',
                          zIndex: 1060,
                          backgroundColor: 'white',
                          padding: '20px',
                          borderRadius: '8px',
                          boxShadow: '0 0 10px rgba(0,0,0,0.25)',
                        }}
                      >
                        <DaumPostcode onComplete={handleComplete} />
                        <button
                          type="button"
                          className="btn btn-outline-danger mt-2"
                          onClick={() => setIsPostOpen(false)}
                        >
                          닫기
                        </button>
                      </div>
                    )}
                  </form>
                </div>

                <div className="modal-footer d-flex justify-content-center" style={{ borderTop: 'none' }}>
                  <button type="button" className="btn btn-outline-danger me-2" onClick={closePopup}>
                    취소
                  </button>
                  <button
                    type="submit"
                    className="btn btn-outline-primary"
                    form="form"
                    onClick={handleSubmit}

                  >
                    등록
                  </button>
                </div>

              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default HQInsertPopUp;
