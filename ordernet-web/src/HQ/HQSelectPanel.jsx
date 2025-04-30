function HQSelectPanel() {
  return (
      <div>
        
        <div
            className="p-3 bg-info bg-opacity-10 border d-flex align-items-center gap-3"
            style={{flexWrap: 'nowrap', overflowX: 'auto'}}
        >

          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 가맹점 Code</span>
            <select className="form-select flex-shrink-1" style={{minWidth: '120px'}}>
              <option>Code</option>
            </select>
          </label>

          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 지점명</span>
            <select className="form-select flex-shrink-1" style={{minWidth: '120px'}}>
              <option>지점명</option>
            </select>
          </label>

          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 일자</span>
            <input type="date" className="form-control flex-shrink-1" style={{minWidth: '120px'}}/>
            <span className="mx-2">~</span>
            <input type="date" className="form-control flex-shrink-1 ms-1" style={{minWidth: '120px'}}/>
          </label>

          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 주문번호</span>
            <select className="form-select flex-shrink-1" style={{minWidth: '100px'}}>
              <option>주문번호</option>
            </select>
          </label>


          <label className="d-flex align-items-center flex-nowrap">
            <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 주문현황</span>
            <select className="form-select flex-shrink-1" style={{minWidth: '100px'}}>
              <option>주문현황</option>
            </select>
          </label>


          <button className="btn btn-secondary flex-shrink-0">조회</button>
        </div>
      </div>
  );
}


export default HQSelectPanel
