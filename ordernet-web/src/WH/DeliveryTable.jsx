import { useEffect, useState } from "react";
import axios from "axios";
import dayjs from "dayjs";
import {useParams} from "react-router-dom";

// DeliveryTable 컴포넌트: 재고(출고) 데이터를 보여주고 상태를 변경하는 테이블
function DeliveryTable({ filters, setBranchList }) {

    // 재고 및 상태 데이터 관리용 상태 변수
    const [WHM, setWHM] = useState({ stockList: [] });     // 전체 재고 데이터
    const [statusMap, setStatusMap] = useState({});        // 각 주문 항목의 상태 저장

    // 페이징 처리 상태 변수
    const [currentPage, setCurrentPage] = useState(1);     // 현재 페이지 번호
    const itemsPerPage = 10; // 페이지당 보여줄 항목 수

    const { agencyCode } = useParams();// 주소에서 :agencyCode 파라미터 추출

    const userId = agencyCode;

    // 필터가 변경될 때마다 서버에서 데이터 요청
    useEffect(() => {
        console.log("Filters:", filters); // 콘솔 확인용
        selectWHManage(filters);
    }, [filters]);

    // 서버로부터 재고 데이터를 가져오는 함수
    const selectWHManage = (filters) => {
        axios.get("http://localhost:8080/webWh/WHManage", {
            headers: { userId: userId },
            params: filters
        })
            .then(res => {
                const data = res.data;
                setWHM(data);

                // 주문 항목별 상태 초기화
                const newStatusMap = {};
                data.stockList.forEach(row => {
                    newStatusMap[row.orderItemId] =
                        row.orderItemStatus === "출고완료" ? "출고완료" : "출고대기";
                });
                setStatusMap(newStatusMap);

                // 중복 제거한 지점 목록을 상위 컴포넌트로 전달
                const uniqueBranches = Array.from(
                    new Set(data.stockList.map(row => row.branchName).filter(Boolean))
                );
                setBranchList(uniqueBranches);

                // 새로운 필터 적용 시 페이지도 1로 초기화
                setCurrentPage(1);
            })
            .catch(err => {
                console.error("데이터 조회 실패:", err);
            });
    };

    // 저장 버튼 클릭 시 변경된 상태를 서버에 저장
    const handleSave = (e) => {
        e.preventDefault();

        // 상태가 실제로 변경된 항목만 필터링
        const updatedItems = WHM.stockList.filter(row =>
            statusMap[row.orderItemId] !== row.orderItemStatus
        ).map(row => ({
            orderItemId: row.orderItemId,
            orderItemStatus: statusMap[row.orderItemId],
            partId: row.partId,                          // 필수: 백엔드에서 재고 차감용
            orderItemQuantity: row.orderItemQuantity     // 필수: 몇 개 차감할지
        }));

        axios.put("http://localhost:8080/webWh/saveStatus", updatedItems)
            .then(res => {
                alert(`${updatedItems.length} 개 항목이 상태 변경되었습니다.`);
                // 저장 후 새로고침
                selectWHManage(filters);
            })
            .catch(err => {
                console.error("저장 실패:", err);
                alert("저장 실패. 콘솔 로그를 확인하세요.");
            });
    };

    // 정렬 및 페이징 데이터 계산
    const sortedList = WHM.stockList?.sort((a, b) => {
        // 출고대기가 위에 오도록 정렬
        if (a.orderItemStatus === "출고대기" && b.orderItemStatus !== "출고대기") return -1;
        if (a.orderItemStatus !== "출고대기" && b.orderItemStatus === "출고대기") return 1;

        // 납기일이 빠른 순서로 정렬
        const diffA = dayjs(a.orderDueDate).diff(dayjs(), "day");
        const diffB = dayjs(b.orderDueDate).diff(dayjs(), "day");

        return diffA - diffB;
    }) || [];

    // 페이징을 위한 데이터 슬라이싱
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = sortedList.slice(indexOfFirstItem, indexOfLastItem);

    // 총 페이지 수 계산
    const totalPages = Math.ceil(sortedList.length / itemsPerPage);

    return (
        <div>
            <div className="p-4 mt-4 bg-light w-100 overflow-auto">
                {/* 저장 버튼 */}
                <div className="mb-3 d-flex justify-content-end">
                    <button className="btn btn-primary" onClick={handleSave}>저장</button>
                </div>

                {/* 테이블 본문 */}
                <table className="table table-bordered">
                    <thead className="table-primary">
                    <tr>
                        <th className="text-center align-middle">주문번호</th>
                        <th className="text-center align-middle">부품코드</th>
                        <th className="text-center align-middle">부품 명</th>
                        <th className="text-center align-middle">카테고리</th>
                        <th className="text-center align-middle">수량</th>
                        <th className="text-center align-middle">지점명</th>
                        <th className="text-center align-middle">납기일</th>
                        <th className="text-center align-middle">상태</th>
                    </tr>
                    </thead>
                    <tbody>
                    {/* 현재 페이지에 해당하는 항목만 출력 */}
                    {currentItems.map((row, i) => {
                        const diff = dayjs(row.orderDueDate).diff(dayjs(), "day");
                        const isDueSoon = row.orderItemStatus === "출고대기" && diff >= 0 && diff <= 3;

                        return (
                            <tr key={i}>
                                <td className="text-center align-middle">{row.orderId}</td>
                                <td className="text-center align-middle">{row.partId}</td>
                                <td className="text-center align-middle">{row.partName}</td>
                                <td className="text-center align-middle">{row.partCate}</td>
                                <td className="text-center align-middle">{row.orderItemQuantity}</td>
                                <td className="text-center align-middle">{row.branchName}</td>
                                <td className={`text-center align-middle ${isDueSoon ? 'text-danger' : ''}`}>
                                    {dayjs(row.orderDueDate).format("YYYY-MM-DD")}
                                </td>
                                <td className="text-center align-middle">
                                    <select
                                        className="form-select"
                                        value={statusMap[row.orderItemId] || "출고대기"}
                                        disabled={row.orderItemStatus === "출고완료"}  // ← 추가
                                        onChange={(e) =>
                                            setStatusMap({
                                                ...statusMap,
                                                [row.orderItemId]: e.target.value,
                                            })
                                        }
                                    >
                                        <option value="출고대기">출고대기</option>
                                        <option value="출고완료">출고완료</option>
                                    </select>
                                </td>
                            </tr>
                        );
                    })}
                    </tbody>
                </table>

                {/* 페이지네이션 */}
                <nav className="d-flex justify-content-center mt-3">
                    <ul className="pagination">
                        {[...Array(totalPages)].map((_, idx) => (
                            <li
                                key={idx}
                                className={`page-item ${currentPage === idx + 1 ? "active" : ""}`}
                            >
                                <button className="page-link" onClick={() => setCurrentPage(idx + 1)}>
                                    {idx + 1}
                                </button>
                            </li>
                        ))}
                    </ul>
                </nav>
            </div>
        </div>
    );
}

export default DeliveryTable;
