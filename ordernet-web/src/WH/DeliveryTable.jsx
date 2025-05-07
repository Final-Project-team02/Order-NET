import {useEffect, useState} from "react";
import axios from "axios";
import dayjs from 'dayjs';

function DeliveryTable() {

  useEffect(() => {
    selectWHManage();
  }, []);





  const [WHM, setWHM] = useState({ whList: [] });

  const  selectWHManage= () => {
    axios.get("http://localhost:8080/WHManage")
        .then(res => {
          console.log('물류센터 재고관리 페이지 조회 성공');
          console.log(res.data);
          setWHM(res.data);
        })
        .catch(err => {
          console.log("비동기 통신 중 오류가 발생했습니다.");
          console.log(err);
        });
  }




  // 부품 고유 ID,  부품명, 부품코드 번호, 부품카테고리, 총 수량, 부품이 속한 ID, 대기상태

    return (
        <div>

          <div className="p-4 mt-5 bg-light w-100 overflow-auto">

            {/* 🔵 여기에 버튼 추가 */}
            <div className="mb-3 d-flex justify-content-end">
              <button className="btn btn-primary" onClick={() => alert('버튼 클릭됨')}>
                저장
              </button>
            </div>

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
                    ?.sort((a, b) => { //sort 를 사용해서 데이터 정렬
                        // 1. 납품기한이 3일 이내인 항목을 우선으로 정렬
                        const isDueSoonA = dayjs(a.orderDueDate).diff(dayjs(), 'day') <= 3;
                        const isDueSoonB = dayjs(b.orderDueDate).diff(dayjs(), 'day') <= 3;

                        // 2. 납품기한이 3일 이내인 항목이 위로 오게 정렬 (true -> 1, false -> 0)
                        if (isDueSoonA && !isDueSoonB) return -1; // A가 우선
                        if (!isDueSoonA && isDueSoonB) return 1; // B가 우선

                        // 3. 그 외의 항목은 날짜순으로 정렬
                        return dayjs(a.orderDueDate).isBefore(dayjs(b.orderDueDate)) ? -1 : 1;
                    })
                    .map((row, i) => {
                        const dueDate = dayjs(row.orderDueDate);
                        const today = dayjs();
                        const diffDays = dueDate.diff(today, 'day');
                        const isDueSoon = diffDays >= 0 && diffDays <= 3;

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
                            {/*    조건에 맞으면 빨간색 텍스트 적용 */}
                            </td>
                            <td className="text-center align-middle text-primary">
                                <select className="block mx-auto mt-1 border rounded px-2 py-1 text-sm">
                                    <option value="ordered">{row.orderItemStatus}</option>
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

export default DeliveryTable