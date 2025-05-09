import { useEffect, useState } from "react";
import axios from "axios";
import dayjs from "dayjs";

function DeliveryTable() {
    const [WHM, setWHM] = useState({ whList: [] });  // 물류센터 재고 관리 데이터 상태
    const [statusMap, setStatusMap] = useState({});   // 각 항목의 상태를 관리할 맵

    // 컴포넌트가 처음 렌더링될 때 데이터 요청
    useEffect(() => {
        selectWHManage();
    }, []);

    // 물류센터 재고 관리 데이터를 가져오는 함수
    const selectWHManage = () => {
        axios.get("http://localhost:8080/WHManage", {
            headers: {
                userId: "WH_BRK"  // 여기에 전달할 userId 값을 지정 (예: 로그인한 사용자 ID)
            }
        })
            .then(res => {
                // 헤더로 userId 전달
                console.log(JSON.stringify(res.data, null, 2));
                setWHM(res.data);  // 받아온 데이터를 상태에 저장

                // 상태값 초기화 (출고완료면 그대로, 아니면 출고대기)
                const newStatusMap = {};
                res.data.whList.forEach(row => {
                    newStatusMap[row.orderId] =
                        row.orderItemStatus === "출고완료" ? "출고완료" : "출고대기";
                });
                setStatusMap(newStatusMap);  // 상태 맵 업데이트
            })
            .catch(err => {
                console.log("비동기 통신 중 오류가 발생했습니다.");
                console.log(err);
            });
    };

    const handleSave = (e) => {
        e.preventDefault();

        // 상태가 실제로 변경된 항목만 선택
        const updatedItems = WHM.whList.filter(row => {
            return statusMap[row.orderId] !== row.orderItemStatus; // 상태가 변경된 항목만 필터링
        }).map(row => ({
            orderItemId: row.orderItemId,
            orderItemStatus: statusMap[row.orderId]
        }));

        axios.put("http://localhost:8080/saveStatus", updatedItems)
            .then(res => {
                alert(`${updatedItems.length} 개 항목이 상태 변경되었습니다.`);  // 변경된 항목 개수만 알림
                console.log("전체 수정 성공:", res.data);

                // 저장이 완료된 후 화면을 새로고침
                window.location.reload();
            })
            .catch(err => {
                console.error("전체 수정 중 오류 발생:", err);
                alert("저장 실패. 콘솔 로그를 확인하세요.");
            });
    };

    return (
        <div>
            <div className="p-4 mt-5 bg-light w-100 overflow-auto">
                {/* 저장 버튼 */}
                <div className="mb-3 d-flex justify-content-end">
                    <button className="btn btn-primary" onClick={handleSave}>
                        저장
                    </button>
                </div>

                {/* 테이블로 물류센터 재고 관리 목록 표시 */}
                <table className="table table-bordered">
                    <thead className="table-primary">
                    <tr>
                        <th className="text-center align-middle">주문번호</th>
                        <th className="text-center align-middle">부품코드 번호</th>
                        <th className="text-center align-middle">부품 명</th>
                        <th className="text-center align-middle">부품 카테고리</th>
                        <th className="text-center align-middle">주문수량</th>
                        <th className="text-center align-middle">대리점명</th>
                        <th className="text-center align-middle">납품기간</th>
                        <th className="text-center align-middle">주문상태</th>
                    </tr>
                    </thead>
                    <tbody>
                    {WHM.whList
                        ?.sort((a, b) => {
                            // 출고대기 → 출고대기 + 3일 이내 → 납기순
                            if (a.orderItemStatus === "출고대기" && b.orderItemStatus !== "출고대기") return -1;
                            if (a.orderItemStatus !== "출고대기" && b.orderItemStatus === "출고대기") return 1;

                            const dueDateA = dayjs(a.orderDueDate);
                            const dueDateB = dayjs(b.orderDueDate);
                            const today = dayjs();
                            const diffDaysA = dueDateA.diff(today, "day");
                            const diffDaysB = dueDateB.diff(today, "day");

                            if (a.orderItemStatus === "출고대기" && diffDaysA <= 3 && diffDaysB > 3) return -1;
                            if (a.orderItemStatus === "출고대기" && diffDaysA > 3 && diffDaysB <= 3) return 1;

                            return diffDaysA - diffDaysB;
                        })
                        .map((row, i) => {
                            const dueDate = dayjs(row.orderDueDate);
                            const today = dayjs();
                            const diffDays = dueDate.diff(today, "day");

                            // 출고대기 상태 + 3일 이내인 경우만 빨간색
                            const isDueSoon = row.orderItemStatus === "출고대기" && diffDays >= 0 && diffDays <= 3;

                            return (
                                <tr key={i}>
                                    <td className="text-center align-middle">{row.orderId}</td>
                                    <td className="text-center align-middle">{row.partId}</td>
                                    <td className="text-center align-middle">{row.partName}</td>
                                    <td className="text-center align-middle">{row.partCate}</td>
                                    <td className="text-center align-middle">{row.orderItemQuantity}</td>
                                    <td className="text-center align-middle">{row.branchName}</td>
                                    <td className={`text-center align-middle ${isDueSoon ? 'text-danger' : ''}`}>
                                        {row.orderDueDate}
                                    </td>
                                    <td className="text-center align-middle text-primary">
                                        <select
                                            className="block mx-auto mt-1 border rounded px-2 py-1 text-sm"
                                            value={statusMap[row.orderId] || "출고대기"}
                                            onChange={(e) =>
                                                setStatusMap({
                                                    ...statusMap,
                                                    [row.orderId]: e.target.value,
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
            </div>
        </div>
    );
}

export default DeliveryTable;
