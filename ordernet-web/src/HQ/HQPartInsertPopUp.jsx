// PartInsertPopUp.jsx
import React, { useState } from 'react';

function PartInsertPopUp({ isOpen, onClose }) {
  const [formData, setFormData] = useState({
    partId: '',
    partName: '',
    partCode: '',
    partImage: null,
    warehouseName: '',
    quantity: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    setFormData(prev => ({
      ...prev,
      partImage: e.target.files[0],
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);

    // 파일 포함 formData 전송용 예시
    const data = new FormData();
    data.append('partId', formData.partId);
    data.append('partName', formData.partName);
    data.append('partCode', formData.partCode);
    data.append('partImage', formData.partImage);
    data.append('warehouseName', formData.warehouseName);
    data.append('quantity', formData.quantity);

    // TODO: 서버로 Axios POST 전송 가능

    onClose(); // 팝업 닫기
  };

  const handleClose = () => {
    onClose();
  };

  if (!isOpen) return null;  // 열리지 않으면 렌더 X

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
                <h5 className="modal-title text-center"><b>부품 등록</b></h5>
              </div>

              <div className="modal-body">
                <form onSubmit={handleSubmit}>
                  <div className="mb-4 mt-3">
                    <input type="text" className="form-control" placeholder="부품 ID" name="partId" value={formData.partId} onChange={handleChange} />
                  </div>

                  <div className="mb-4">
                    <input type="text" className="form-control" placeholder="부품명" name="partName" value={formData.partName} onChange={handleChange} />
                  </div>

                  <div className="mb-4">
                    <input type="text" className="form-control" placeholder="부품코드 번호" name="partCode" value={formData.partCode} onChange={handleChange} />
                  </div>

                  <div className="mb-4 d-flex align-items-center">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="첨부파일 선택"
                        style={{ flex: 3 }}
                        value={formData.partImage ? formData.partImage.name : ''}
                        disabled
                    />
                    <label className="btn btn-secondary" style={{ backgroundColor: '#cfe2ff', color: 'black' }}>
                      첨부파일
                      <input
                          type="file"
                          name="partImage"
                          onChange={handleFileChange}
                          style={{ display: 'none' }}
                      />
                    </label>
                  </div>

                  <div className="mb-4">
                    <input type="text" className="form-control" placeholder="물류센터 명" name="warehouseName" value={formData.warehouseName} onChange={handleChange} />
                  </div>

                  <div className="mb-4">
                    <input type="number" className="form-control" placeholder="수량" name="quantity" value={formData.quantity} onChange={handleChange} />
                  </div>
                </form>
              </div>

              <div className="modal-footer d-flex justify-content-center" style={{ borderTop: 'none' }}>
                <button type="button" className="btn btn-outline-danger me-2" onClick={handleClose}>
                  취소
                </button>
                <button type="button" className="btn btn-outline-primary" onClick={handleSubmit}>
                  등록
                </button>
              </div>

            </div>
          </div>
        </div>
      </>
  );
}

export default PartInsertPopUp;
