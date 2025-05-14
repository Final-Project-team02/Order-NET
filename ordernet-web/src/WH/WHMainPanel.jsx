import {LogIn, LogOut, BarChart2 } from "lucide-react";
import {useEffect, useState} from "react";
import GraphImage from "../assets/Graph.png";
import axios from "axios";
import {useParams} from "react-router-dom";

function WHMainPanel() {

    useEffect(() => {
        selectWHMain();
    }, []);

    const [isGraphChecked, setIsGraphChecked] = useState(false);

    const [whData, setWhData] = useState({ comeInList: [], stockList: [] });



    const { agencyCode } = useParams();  // 주소에서 :agencyCode 파라미터 추출
    const userId = agencyCode;

    const  selectWHMain= () => {
        axios.get("http://localhost:8080/WHMain", {
            headers: {
                userId: userId  // 여기에 전달할 userId 값을 지정 (예: 로그인한 사용자 ID)
            }
        }
    )
            .then(res => {
                console.log('물류센터 재고현황 페이지 조회 성공');
                console.log(res.data);
                setWhData(res.data);
            })
            .catch(err => {
                console.log("비동기 통신 중 오류가 발생했습니다.");
                console.log(err);
            });
    }

    return (
        <div>
            <div className="p-4 mt-3 bg-light w-100 overflow-auto">
                {/*<h2 className="h5 fw-bold mb-3">입고리스트</h2>*/}

                <div className="d-flex justify-content-between align-items-center text-primary mb-3">
                    <h5 className="fw-bold mb-0 d-flex align-items-center gap-2">
                        {isGraphChecked ? (
                            <BarChart2 className="cursor-pointer text-primary" size={20} strokeWidth={2}/>
                        ) : (
                            <LogIn className="cursor-pointer text-primary" size={20} strokeWidth={2}/>
                        )}
                        {isGraphChecked ? "입고리스트주문대비출고그래프" : "입고리스트"}
                    </h5>


                    <div className="form-check">
                        <input className="form-check-input" type="checkbox" id="graphCheck"
                               checked={isGraphChecked} onChange={(e) => setIsGraphChecked(e.target.checked)}/>
                        <label className="form-check-label text-primary" htmlFor="graphCheck">
                            GRAPH
                        </label>
                    </div>
                </div>

                <hr></hr>

                {/* 테이블 , 체크박스 체크 시, 테이블 대신,이미지 파일이 들어감.*/}
                {/* 테이블 또는 그래프를 조건부로 표시 */}
                {isGraphChecked ? (
                    <div className="text-center mt-4">
                        <img src={GraphImage} alt="그래프" style={{ width: "100%", height: "auto" }} />
                    </div>
                ) : (
                    <div className="mt-4">
                    <table className="table table-bordered">
                        <thead className="table-primary">
                        <tr>
                            <th className="text-center align-middle">부품 코드번호</th>
                            <th className="text-center align-middle">부품 명</th>
                            <th className="text-center align-middle">부품 가격</th>
                            <th className="text-center align-middle">부품 카테고리</th>
                            <th className="text-center align-middle">입고량</th>
                            <th className="text-center align-middle">입고날짜 </th>

                        </tr>
                        </thead>
                        <tbody>

                        {whData.comeInList.map((row, i) => (
                            <tr key={row.partId + '-' + i}>
                                <td className="text-center align-middle">{row.partId}</td>
                                <td className="text-center align-middle">{row.partName}</td>
                                <td className="text-center align-middle">  {Number(row.partPrice).toLocaleString()} (원)</td>
                                <td className="text-center align-middle">{row.partCate}</td>
                                <td className="text-center align-middle">{row.inboundQuantity}</td>
                                <td className="text-center align-middle">{row.inboundDate}</td>
                            </tr>
                        ))}

                        </tbody>
                    </table>
                    </div>
                )}
            </div>

            {/*<hr></hr>*/}

            {/*<h2 className="p-1 h5 fw-bold mb-3">재고</h2>*/}
            {/*<hr></hr>*/}

            <div className="p-4 mt-3 bg-light w-100 overflow-auto">
                <div className="d-flex justify-content-between align-items-center text-primary mb-3">
                    <div className="d-flex align-items-center gap-2">
                        <LogOut
                            className="cursor-pointer text-primary"
                            size={20}
                            strokeWidth={2}
                        />
                        <h5 className="fw-bold mb-0">재고</h5>
                    </div>
                </div>

                {/*<h2 className="h5 fw-bold mb-3 text-primary">재고</h2>*/}

                <hr></hr>

                <div className="mt-4">
                <table className="table table-bordered">
                    <thead className="table-primary">
                    <tr>
                        <th className="text-center align-middle">부품 코드번호</th>
                        <th className="text-center align-middle">부품명</th>
                        <th className="text-center align-middle">부품 카테고리</th>
                        <th className="text-center align-middle">재고량</th>
                        <th className="text-center align-middle">부품가격</th>
                    </tr>
                    </thead>
                    <tbody>
                    {whData.stockList.map((row, i) => (
                        <tr key={i}>
                            <td className="text-center align-middle">{row.partId}</td>
                            <td className="text-center align-middle">{row.partName}</td>
                            <td className="text-center align-middle">{row.partCate}</td>
                            <td className="text-center align-middle">{row.stockQuantity}</td>
                            <td className="text-center align-middle">{Number(row.partPrice).toLocaleString()} (원)</td>
                        </tr>
                    ))}

                    </tbody>
                </table>
                </div>

            </div>
            <hr></hr>
        </div>
    );
}

export default WHMainPanel

