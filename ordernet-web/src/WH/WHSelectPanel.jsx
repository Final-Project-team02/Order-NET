import { useState } from "react";

function WHSelectPanel({ onSearch, branchOptions = [] }) {
    const [filters, setFilters] = useState({
        orderItemStatus: '',
        branchName: '',
        orderId: '',
        orderStartDate: '',
        orderEndDate: ''
    });

    const orderStatusOptions = ['전체', '출고대기', '출고완료'];

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({ ...prev, [name]: value }));
    };

    const handleSearch = () => {
        // 빈 값이 있는 필드 제거
        const cleanFilters = Object.fromEntries(
            Object.entries(filters).filter(([key, value]) => value !== "" && value != null)
        );

        // 부모 컴포넌트로 필터 값 전달
        if (onSearch) onSearch(cleanFilters);
    };

    return (
        <div className="mt-5 p-3 bg-secondary bg-opacity-10 border d-flex align-items-center gap-3" style={{ flexWrap: 'nowrap', overflowX: 'auto' }}>
            {/* 필터링 입력 UI */}
            <label className="d-flex align-items-center flex-nowrap">
                <span className="fw-bold me-2" style={{ whiteSpace: 'nowrap', width: '90px' }}>● 주문 상태</span>
                <select
                    className="form-select flex-shrink-1"
                    name="orderItemStatus"
                    style={{ minWidth: '180px' }}
                    onChange={handleChange}
                    value={filters.orderItemStatus}
                >
                    {orderStatusOptions.map((status, idx) => (
                        <option key={idx} value={status === '전체' ? '' : status}>{status}</option>
                    ))}
                </select>
            </label>

            {/* 지점 필터 */}
            <label className="d-flex align-items-center flex-nowrap">
                <span className="fw-bold me-2" style={{ whiteSpace: 'nowrap', width: '90px' }}>● 지점명</span>
                <select
                    className="form-select flex-shrink-1"
                    name="branchName"
                    style={{ minWidth: '180px' }}
                    onChange={handleChange}
                    value={filters.branchName}
                >
                    <option value="">전체</option>
                    {branchOptions.map((branch, idx) => (
                        <option key={idx} value={branch}>{branch}</option>
                    ))}
                </select>
            </label>

            {/* 날짜 필터 */}
            <label className="d-flex align-items-center flex-nowrap">
                <span className="fw-bold me-2" style={{ whiteSpace: 'nowrap', width: '90px' }}>● 일자</span>
                <input
                    type="date"
                    name="orderStartDate"
                    className="form-control flex-shrink-1"
                    style={{ minWidth: '160px' }}
                    onChange={handleChange}
                    value={filters.orderStartDate}
                />
                <span className="mx-2">~</span>
                <input
                    type="date"
                    name="orderEndDate"
                    className="form-control flex-shrink-1"
                    style={{ minWidth: '160px' }}
                    onChange={handleChange}
                    value={filters.orderEndDate}
                />
            </label>

            {/* 주문번호 필터 */}
            <label className="d-flex align-items-center flex-nowrap">
                <span className="fw-bold me-2" style={{ whiteSpace: 'nowrap', width: '90px' }}>● 주문번호</span>
                <input
                    type="text"
                    name="orderId"
                    className="form-control"
                    placeholder="주문번호 입력"
                    onChange={handleChange}
                    value={filters.orderId}
                    style={{ minWidth: '160px' }}
                />
            </label>

            <button className="btn btn-primary flex-shrink-0 rounded" onClick={handleSearch}>검색</button>
        </div>
    );
}

export default WHSelectPanel;
