import React, { useState } from 'react';
import DaumPostcode from 'react-daum-postcode';
import axios from "axios";


function HQInsertPopUp({ isOpen, onClose }) {
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
    city: '',  // 도시명 추가
  });

  const handleComplete = (data) => {
    console.log("Received sido:", data.sido);
    const sidoToEnglish = {
      '서울': 'Seoul',
      '부산': 'Busan',
      '대구': 'Daegu',
      '인천': 'Incheon',
      '광주': 'Gwangju',
      '대전': 'Daejeon',
      '울산': 'Ulsan',
      '세종': 'Sejong',
      '경기도': 'Gyeonggi',
      '강원도': 'Gangwon',
      '충청북도': 'Chungbuk',
      '충청남도': 'Chungnam',
      '전라북도': 'Jeonbuk',
      '전라남도': 'Jeonnam',
      '경상북도': 'Gyeongbuk',
      '경상남도': 'Gyeongnam',
      '제주특별자치도': 'Jeju',
    };

    let englishCity = sidoToEnglish[data.sido];
    console.log("englishCity before check:", englishCity);

    // 없는 경우, 광역시나 특별시를 제외한 도시명만 추출
    if (!englishCity) {
      if (data.sido.includes('광역시') || data.sido.includes('특별시')) {
        englishCity = data.sido.replace(/(광역시|특별시)/g, '').trim(); // '광역시'나 '특별시'를 제거
        console.log("City after remove 광역시/특별시:", englishCity);
      } else {
        englishCity = 'Unknown';  // 매핑에 없는 경우
      }
    }
    console.log("Mapped city:", englishCity);
    setFormData((prev) => ({
      ...prev,
      branchZipCode: data.zonecode,
      branchRoadAddr: data.roadAddress,
      city: englishCity, // 수정된 city
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

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('등록할 대리점 정보:', formData);
    // TODO: Axios POST 요청 가능

    try {
      const res = await axios.post('http://localhost:8080/HQMain/insertclient', formData,
          {
            headers: {
              'Content-Type': 'application/json'
            }
          });
      console.log("서버 응답:", res.data);
      alert("대리점이 성공적으로 등록되었습니다.");
      onClose();  // 등록 후 팝업 닫기
    } catch (error) {
      console.error("등록 실패:", error);
      alert("대리점 등록 중 오류가 발생했습니다.");
    }
  };

  const handleClose = () => {
    setIsPostOpen(false);
    onClose();
  };

  if (!isOpen) return null;  // isOpen이 false면 렌더 안 함

  return (
      <>
        <style>
          {`
        input::placeholder,
        textarea::placeholder {
          color: #6c757d !important;
          opacity: 1;
        }
      `}
        </style>
        <div
            className="modal-backdrop fade show" onClick={(e) => { if (e.target === e.currentTarget) handleClose(); }} />

        <div className="modal fade show"
             tabIndex="-1"
             role="dialog"
             style={{
               display: 'block',
               position: 'fixed',
               top: '50%',
               left: '50%',
               transform: 'translate(-50%, -50%)',
               zIndex: 1050,
               height: 'auto',
             }}
             onClick={(e) => { if (e.target === e.currentTarget) handleClose(); }}
        >
          <div className="modal-dialog modal-fullscreen-md-down" role="document">
            <div className="modal-content">
              <div className="modal-header d-flex justify-content-center" style={{ backgroundColor: '#cfe2ff' }}>
                <h5 className="modal-title text-center"><b>대리점 등록</b></h5>
              </div>

              <div className="modal-body">
                <form onSubmit={handleSubmit}>
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
                      <input type="text" name="userId" value={formData.userId} onChange={handleChange} className="form-control" placeholder="대리점 ID" />
                    </div>
                    <div className="col">
                      <input type="password" name="userPw" value={formData.userPw} onChange={handleChange} className="form-control" placeholder="비밀번호" />
                    </div>
                  </div>

                  <div className="mb-3">
                    <input type="text" name="branchPhone" value={formData.branchPhone} onChange={handleChange} className="form-control" placeholder="전화번호" />
                  </div>

                  <div className="mb-3 d-flex align-items-center">
                    <label className="me-2">주소구분</label>
                    <div>
                      <input type="radio" id="road" name="addressType" defaultChecked readOnly />
                      <label htmlFor="road" className="ms-1">도로명</label>
                    </div>
                  </div>

                  <div className="d-flex mb-3">
                    <input type="text" name={"branchZipCode"} className="form-control" style={{ flex: 3 }} placeholder="우편번호" value={formData.branchZipCode} readOnly />
                    <button type="button" className="btn btn-secondary"
                            onClick={() => setIsPostOpen(true)}
                            style={{ flex: 0.3, backgroundColor: '#cfe2ff', color: 'black' }} >
                      검색
                    </button>
                  </div>

                  <div className="mb-3">
                    <input type="text" name="branchRoadAddr" className="form-control" placeholder="주소" value={formData.branchRoadAddr} readOnly />
                  </div>

                  <div className="mb-3">
                    <input type="text" name="branchDetailAddr" className="form-control" placeholder="상세주소 (예: 101동 202호)" value={formData.branchDetailAddr} onChange={handleChange} />
                  </div>

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
                        <button type="button" className="btn btn-outline-danger mt-2" onClick={() => setIsPostOpen(false)}>
                          닫기
                        </button>
                      </div>
                  )}
                </form>
              </div>

              <div className="modal-footer d-flex justify-content-center" style={{ borderTop: 'none' }}>
                <button type="button" className="btn btn-outline-danger me-2" onClick={handleClose}>취소</button>
                <button type="submit" className="btn btn-outline-primary" onClick={handleSubmit}>등록</button>
              </div>

            </div>
          </div>
        </div>
      </>
  );
}

export default HQInsertPopUp;
