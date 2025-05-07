import {LogIn, LogOut, BarChart2 } from "lucide-react";
import {useState} from "react";
import GraphImage from "../assets/Graph.png";

function WHMainPanel() {

    const [isGraphChecked, setIsGraphChecked] = useState(false);

  // 부품 고유 ID,  부품명, 부품코드 번호, 부품카테고리,입고량,카테고리
  const rows = [
    { stockId:'ABC-1234',  name: '브레이크 패드', stockCodeId: 'A2345', warehouseCate: '브레이크', quantity: '3', CateNumber: '100'  },
    { stockId:'DKF-5223',  name: '크랭크샤프트', stockCodeId: 'AB12', warehouseCate: '엔진', quantity: '2', CateNumber: '100'  },
    { stockId:'EFW-1234',  name: '타이밍 벨트', stockCodeId: 'CADS1', warehouseCate: '엔진', quantity: '1', CateNumber: '100'  },
    { stockId:'CFW-3145',  name: '서스펜션 패드', stockCodeId: '4124', warehouseCate: '서스펜션', quantity: '1', CateNumber: '100'  },
    { stockId:'ABC-1234',  name: '브레이크 패드', stockCodeId: 'A2345', warehouseCate: '브레이크', quantity: '3', CateNumber: '100'  },
    { stockId:'DKF-5223',  name: '크랭크샤프트', stockCodeId: 'AB12', warehouseCate: '엔진', quantity: '2', CateNumber: '100'  },
    { stockId:'EFW-1234',  name: '타이밍 벨트', stockCodeId: 'CADS1', warehouseCate: '엔진', quantity: '1', CateNumber: '100'  },
    { stockId:'CFW-3145',  name: '서스펜션 패드', stockCodeId: '4124', warehouseCate: '서스펜션', quantity: '1', CateNumber: '100'  }, ]

    return (
        <div>
            <div className="p-4 mt-3 bg-light w-100 overflow-auto">
                {/*<h2 className="h5 fw-bold mb-3">입고리스트</h2>*/}

                <div className="d-flex justify-content-between align-items-center text-primary mb-3">
                    <div className="fw-bold d-flex align-items-center gap-2">
                        {isGraphChecked ? (
                            <BarChart2 className="cursor-pointer text-primary" size={20} strokeWidth={2} />
                        ) : (
                            <LogIn className="cursor-pointer text-primary" size={20} strokeWidth={2} />
                        )}
                        {isGraphChecked ? "입고리스트주문대비출고그래프" : "입고리스트"}

                    </div>
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
                        <thead className="table-info">
                        <tr>
                            <th className="text-center align-middle">부품 고유 ID</th>
                            <th className="text-center align-middle">부품 명</th>
                            <th className="text-center align-middle">부품코드 번호</th>
                            <th className="text-center align-middle">부품 카테고리</th>
                            <th className="text-center align-middle">입고량</th>
                            <th className="text-center align-middle">카테고리</th>
                        </tr>
                        </thead>
                        <tbody>
                        {rows.map((row, i) => (
                            // <tr key={i}>
                            <tr key={row.stockId + '-' + i}>
                                <td className="text-center align-middle">{row.stockId}</td>
                                <td className="text-center align-middle">{row.name}</td>
                                <td className="text-center align-middle">{row.stockCodeId}</td>
                                <td className="text-center align-middle">{row.warehouseCate}</td>
                                <td className="text-center align-middle">{row.quantity}</td>
                                <td className="text-center align-middle">{row.CateNumber}</td>
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
                    <thead className="table-info">
                    <tr>
                        <th className="text-center align-middle">부품 고유 ID</th>
                        <th className="text-center align-middle">부품명</th>
                        <th className="text-center align-middle">부품코드 번호</th>
                        <th className="text-center align-middle">부품 카테고리</th>
                        <th className="text-center align-middle">재고량</th>
                        <th className="text-center align-middle">카테고리</th>
                    </tr>
                    </thead>
                    <tbody>
                    {rows.map((row, i) => (

                        <tr key={i}>
                            <td className="text-center align-middle">{row.stockId}</td>
                            <td className="text-center align-middle">{row.name}</td>
                            <td className="text-center align-middle">{row.stockCodeId}</td>
                            <td className="text-center align-middle">{row.warehouseCate}</td>
                            <td className="text-center align-middle">{row.quantity}</td>
                            <td className="text-center align-middle">{row.CateNumber}</td>
                        </tr>
                    ))
                    }
                    </tbody>
                </table>
                </div>

            </div>
            <hr></hr>
        </div>
    );
}

export default WHMainPanel

