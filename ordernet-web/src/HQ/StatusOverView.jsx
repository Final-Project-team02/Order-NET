import React, { useEffect, useState } from "react";

function StatusOverView() {
    const [kpiData, setKpiData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/HQDashBoard/order/stats")
            .then((res) => {
                if (!res.ok) throw new Error("서버 응답 오류");
                return res.json();
            })
            .then((data) => {
                setKpiData([
                    {
                        label: "총 주문",
                        value: data.totalOrders,
                        color: "#3b82f6",
                        description: "전체 주문 수량입니다.",
                    },
                    {
                        label: "출고 완료",
                        value: data.shippedOrders,
                        color: "#10b981",
                        description: "출고가 완료된 주문입니다.",
                    },
                    {
                        label: "반려",
                        value: data.deniedOrders,
                        color: "#ef4444",
                        description: "반려된 주문입니다.",
                    },
                    {
                        label: "승인 대기",
                        value: data.pendingOrders,
                        color: "#fbbf24",
                        description: "승인 대기 중인 주문입니다.",
                    },
                ]);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    if (loading) return <div>로딩 중...</div>;
    if (error) return <div>에러 발생: {error}</div>;

    return (
        <div
            style={{
                display: "flex",
                gap: 20,
                flexWrap: "wrap",
                justifyContent: "center",
            }}
        >
            {kpiData.map(({ label, value, color, description }, idx) => (
                <div
                    key={idx}
                    style={{
                        flex: "1 1 300px",
                        backgroundColor: "#fff",
                        borderRadius: 12,
                        boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
                        padding: 20,
                        display: "flex",
                        alignItems: "center",
                        gap: 20,
                    }}
                >
                    {/* 왼쪽: 숫자 & 라벨 */}
                    <div
                        style={{
                            flexShrink: 0,
                            textAlign: "center",
                            minWidth: 90,
                            borderRight: `3px solid ${color}`,
                            paddingRight: 15,
                        }}
                    >
                        <div
                            style={{
                                fontSize: 36,
                                fontWeight: "bold",
                                color: color,
                                lineHeight: 1,
                            }}
                        >
                            {value}건
                        </div>
                        <div style={{ marginTop: 6, fontSize: 18, color: "#555" }}>
                            {label}
                        </div>
                    </div>

                    {/* 오른쪽: 설명 */}
                    <div style={{ flexGrow: 1, color: "#666", fontSize: 14 }}>
                        {description}
                    </div>
                </div>
            ))}
        </div>
    );
}

export default StatusOverView;
