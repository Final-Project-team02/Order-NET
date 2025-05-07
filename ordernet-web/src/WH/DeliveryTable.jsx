function DeliveryTable() {


  // 부품 고유 ID,  부품명, 부품코드 번호, 부품카테고리, 총 수량, 부품이 속한 ID, 대기상태
  const rows = [
    { stockId:'ABC-1234',  name: '브레이크 패드', stockCodeId: 'A2345', warehouseCate: '브레이크', quantity: '3', WareHouse: '100', status: '출고 완료'  },
    { stockId:'DKF-5223',  name: '크랭크샤프트', stockCodeId: 'AB12', warehouseCate: '엔진', quantity: '2', WareHouse: '100', status: '출고 완료'  },
    { stockId:'EFW-1234',  name: '타이밍 벨트', stockCodeId: 'CADS1', warehouseCate: '엔진', quantity: '1', WareHouse: '100', status: '출고 완료'  },
    { stockId:'CFW-3145',  name: '서스펜션 패드', stockCodeId: '4124', warehouseCate: '서스펜션', quantity: '1', WareHouse: '100', status: '출고 완료'  },
    { stockId:'ABC-1234',  name: '브레이크 패드', stockCodeId: 'A2345', warehouseCate: '브레이크', quantity: '3', WareHouse: '100', status: '출고 완료'  },
    { stockId:'DKF-5223',  name: '크랭크샤프트', stockCodeId: 'AB12', warehouseCate: '엔진', quantity: '2', WareHouse: '100', status: '출고 완료'  },
    { stockId:'EFW-1234',  name: '타이밍 벨트', stockCodeId: 'CADS1', warehouseCate: '엔진', quantity: '1', WareHouse: '100', status: '출고 완료'  },
    { stockId:'CFW-3145',  name: '서스펜션 패드', stockCodeId: '4124', warehouseCate: '서스펜션', quantity: '1', WareHouse: '100', status: '출고 완료'  },
    { stockId:'ABC-1234',  name: '브레이크 패드', stockCodeId: 'A2345', warehouseCate: '브레이크', quantity: '3', WareHouse: '100', status: '출고 완료'  },
    { stockId:'DKF-5223',  name: '크랭크샤프트', stockCodeId: 'AB12', warehouseCate: '엔진', quantity: '2', WareHouse: '100', status: '출고 완료'  },
    { stockId:'EFW-1234',  name: '타이밍 벨트', stockCodeId: 'CADS1', warehouseCate: '엔진', quantity: '1', WareHouse: '100', status: '출고 완료'  },
    { stockId:'CFW-3145',  name: '서스펜션 패드', stockCodeId: '4124', warehouseCate: '서스펜션', quantity: '1', WareHouse: '100', status: '출고 완료'  },
    { stockId:'ABC-1234',  name: '브레이크 패드', stockCodeId: 'A2345', warehouseCate: '브레이크', quantity: '3', WareHouse: '100', status: '출고 완료'  },
    { stockId:'DKF-5223',  name: '크랭크샤프트', stockCodeId: 'AB12', warehouseCate: '엔진', quantity: '2', WareHouse: '100', status: '출고 완료'  },
    { stockId:'EFW-1234',  name: '타이밍 벨트', stockCodeId: 'CADS1', warehouseCate: '엔진', quantity: '1', WareHouse: '100', status: '출고 완료'  },

  ]

    return (
        <div>

          <div className="p-4 mt-5 bg-light w-100 overflow-auto">
            <table className="table table-bordered">
              <thead className="table-info">
              <tr>
                <th className="text-center align-middle">부품 고유 ID</th>
                <th className="text-center align-middle">부품 명</th>
                <th className="text-center align-middle">부품코드 번호</th>
                <th className="text-center align-middle">부품 카테고리</th>
                <th className="text-center align-middle">총 수량</th>
                <th className="text-center align-middle">부품이 속한 본사 ID</th>
                <th className="text-center align-middle">대기상태</th>
              </tr>
              </thead>
              <tbody >
              {rows.map((row, i) => (
                  <tr key={i}>
                    <td className="text-center align-middle">{row.stockId}</td>
                    <td className="text-center align-middle">{row.name}</td>
                    <td className="text-center align-middle">{row.stockCodeId}</td>
                    <td className="text-center align-middle">{row.warehouseCate}</td>
                    <td className="text-center align-middle">{row.quantity}</td>
                    <td className="text-center align-middle">{row.WareHouse}</td>
                    <td className="text-center align-middle text-primary">{row.status}</td>
                  </tr>
              ))
              }
              </tbody>
            </table>
          </div>
        </div>
    );
}

export default DeliveryTable