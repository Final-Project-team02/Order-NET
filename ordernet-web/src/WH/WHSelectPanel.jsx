function WHSelectPanel() {
    return (
        <div>

          <div
              className="mt-5 p-3 bg-secondary bg-opacity-10 border d-flex align-items-center gap-3"
              style={{flexWrap: 'nowrap', overflowX: 'auto'}}
          >

            <label className="d-flex align-items-center flex-nowrap">
              <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 출고 완료</span>
              <select className="form-select flex-shrink-1" style={{minWidth: '200px'}}>
                <option>Code</option>
              </select>
            </label>

            <label className="d-flex align-items-center flex-nowrap">
              <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 지점명</span>
              <select className="form-select flex-shrink-1" style={{minWidth: '200px'}}>
                <option>지점명</option>
              </select>
            </label>

            <label className="d-flex align-items-center flex-nowrap">
              <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 일자</span>
              <input type="date" className="form-control flex-shrink-1" style={{minWidth: '200px'}}/>
              <span className="mx-2">~</span>
              <input type="date" className="form-control flex-shrink-1 ms-1" style={{minWidth: '200px'}}/>
            </label>

            <label className="d-flex align-items-center flex-nowrap">
              <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 주문번호</span>
              <select className="form-select flex-shrink-1" style={{minWidth: '180px'}}>
                <option>주문번호</option>
              </select>
            </label>

            <button className="btn btn-primary flex-shrink-0 rounded">검색</button>
          </div>
        </div>
    );
}

export default WHSelectPanel