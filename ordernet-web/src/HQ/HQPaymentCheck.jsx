function HQPaymentCheck() {
  const rows = [
    { orderId: '25-001', date: '2024-04-20', name: '엔진', status: '결제' },
    { orderId: '25-002', date: '2024-04-20', name: '사이드 미러', status: '반려' },
    { orderId: '25-003', date: '2024-04-20', name: '엔진', status: '결제' },
    { orderId: '25-004', date: '2024-04-20', name: '사이드 미러', status: '반려' },

  ];

  return (
      <div className="p-4 mt-3 bg-light w-100 overflow-auto">
        <h2 className="h5 fw-bold mb-3">가맹점 주문 내역</h2>
        <table className="table table-bordered">
          <thead className="table-info">
          <tr>
            <th className="text-center align-middle">주문 번호</th>
            <th className="text-center align-middle">주문 날짜</th>
            <th className="text-center align-middle">부품명</th>
            <th className="text-center align-middle">주문 상태</th>
          </tr>
          </thead>
          <tbody>
          {rows.map((row, i) => (
              <tr key={i}>
                <td className="text-center align-middle">{row.orderId}</td>
                <td className="text-center align-middle">{row.date}</td>
                <td className="text-center align-middle">{row.name}</td>
                <td className="text-center align-middle">{row.status}</td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
}


export default HQPaymentCheck

