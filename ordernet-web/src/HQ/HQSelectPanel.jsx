import axios from 'axios';
import { useState } from 'react';

function HQSelectPanel({ onSearch }) {


  const [branchId, setBranchId] = useState('');
  const [branchName, setBranchName] = useState('');
  const [orderStatus, setOrderStatus] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleSearch = () => {

    if (!branchId) {
      alert('대리점 ID를 선택해주세요.');
      return;
    }
    if (!branchName) {
      alert('지점명을 선택해주세요.');
      return;
    }
    if (!orderStatus) {
      alert('주문현황을 선택해주세요.');
      return;
    }
    if (!startDate || !endDate) {
      alert('주문일자를 모두 선택해주세요.');
      return;
    }

    axios.post('http://localhost:8080/HQMain/search', {
      branchId,
      branchName,
      orderStatus,
      startDate,
      endDate
    })
        .then(response => {
          console.log(response.data); // 검색 결과
          // setRows3(response.data);
          onSearch(response.data , orderStatus);    // 부모 컴포넌트에 전달
        })
        .catch(error => {
          console.error(error);
        });
  };

  return (
      <div>

        <div
            className="p-3 bg-secondary bg-opacity-10 border d-flex align-items-center gap-3"
            style={{flexWrap: 'nowrap', overflowX: 'auto'}}>

          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 대리점 ID</span>
            <select className="form-select flex-shrink-1" style={{minWidth: '120px'}} onChange={e => setBranchId(e.target.value)}>
              <option value="">Code</option>
              <option value="Busan-1">Busan-1</option>
              <option value="Daegu-1">Daegu-1</option>
              <option value="Daejeon-1">Daejeon-1</option>
              <option value="Gwangju-1">Gwangju-1</option>
              <option value="Incheon-1">Incheon-1</option>
              <option>Jeju-1</option>
              <option>Sejong-1</option>
              <option>Seoul-1</option>
              <option>Ulsan-1</option>
            </select>
          </label>

          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 지점명</span>
            <select className="form-select flex-shrink-1" style={{minWidth: '120px'}} onChange={e => setBranchName(e.target.value)}>
              <option value="">지점명</option>
              <option value="부산지점">부산지점</option>
              <option value="대구지점">대구지점</option>
              <option value="대전지점">대전지점</option>
              <option value="광주지점">광주지점</option>
              <option value="인천지점">인천지점</option>
              <option value="제주지점">제주지점</option>
              <option value="세종지점">세종지점</option>
              <option value="서울지점">서울지점</option>
              <option value="울산지점">울산지점</option>
            </select>
          </label>

          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 주문일자</span>
            <input type="date" className="form-control flex-shrink-1" style={{minWidth: '120px'}} onChange={e => setStartDate(e.target.value)}/>
            <span className="mx-2">~</span>
            <input type="date" className="form-control flex-shrink-1 ms-1" style={{minWidth: '120px'}} onChange={e => setEndDate(e.target.value)}/>
          </label>


          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 주문현황</span>
            <select className="form-select flex-shrink-1" style={{minWidth: '100px'}}  onChange={e => setOrderStatus(e.target.value)}>
              <option value="">주문현황</option>
              <option value="승인 대기">승인 대기</option>
              <option value="결제">결제</option>
              <option value="반려">반려</option>
            </select>
          </label>


          <button className="btn btn-secondary flex-shrink-0"  onClick={handleSearch}>조회</button>
        </div>

      </div>
  );
}


export default HQSelectPanel





