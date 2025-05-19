import React, { useEffect, useState } from "react";
import { PieChart, Pie, Cell } from "recharts";

function StatusOverView() {
    const [kpiData, setKpiData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/HQDashBoard/order/stats")
            .then((res) => {
                if (!res.ok) throw new Error("서버 응답 오류");
                return res.json();
            })
            .then((data) => {
                const { totalOrders, shippedOrders, deniedOrders, pendingOrders } = data;

                setKpiData([
                    {
                        label: "총 주문",
                        value: totalOrders,
                        color: "#3b82f6",
                        type: "total",
                    },
                    {
                        label: "출고 완료",
                        value: shippedOrders,
                        color: "#10b981",
                    },
                    {
                        label: "반려",
                        value: deniedOrders,
                        color: "#ef4444",
                    },
                    {
                        label: "승인 대기",
                        value: pendingOrders,
                        color: "#fbbf24",
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

    const totalOrders = kpiData.find(item => item.label === "총 주문")?.value || 1;
    const otherStatuses = kpiData.filter(item => item.label !== "총 주문");

    return (
        <div
            style={{
                display: "flex",
                gap: 20,
                flexWrap: "wrap",
                justifyContent: "center",
            }}
        >
            {/* 총 주문 카드 */}
            {kpiData.filter(kpi => kpi.label === "총 주문").map(({ label, value, color }, idx) => (
                <div
                    key={idx}
                    style={{
                        flex: "1 1 300px",
                        backgroundColor: "#fff",
                        borderRadius: 12,
                        boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
                        padding: 20,
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        height: 140,
                    }}
                >
                    <div style={{ textAlign: "center" }}>
                        <div
                            style={{
                                fontSize: 40,
                                fontWeight: "bold",
                                color: color,
                            }}
                        >
                            {value}건
                        </div>
                        <div style={{ marginTop: 6, fontSize: 18, color: "#555" }}>
                            {label}
                        </div>
                    </div>
                </div>
            ))}

            {/* 상태별 카드 */}
            {otherStatuses.map(({ label, value, color }, idx) => {
                const chartData = [
                    { name: label, value: value },
                    { name: "기타", value: totalOrders - value },
                ];

                return (
                    <div
                        key={idx}
                        style={{
                            flex: "1 1 300px",
                            backgroundColor: "#fff",
                            borderRadius: 12,
                            boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
                            display: "flex",
                            height: 140,
                            overflow: "hidden",
                        }}
                    >
                        {/* 왼쪽 50%: 숫자/라벨 */}
                        <div
                            style={{
                                flex: 1,
                                borderRight: `2px solid #e5e7eb`,
                                display: "flex",
                                flexDirection: "column",
                                justifyContent: "center",
                                alignItems: "center",
                                padding: 10,
                            }}
                        >
                            <div
                                style={{
                                    fontSize: 30,
                                    fontWeight: "bold",
                                    color: color,
                                    lineHeight: 1.2,
                                }}
                            >
                                {value}건
                            </div>
                            <div style={{ marginTop: 6, fontSize: 16, color: "#555" }}>
                                {label}
                            </div>
                        </div>

                        {/* 오른쪽 50%: 파이차트 */}
                        <div
                            style={{
                                flex: 1,
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                            }}
                        >
                            <PieChart width={100} height={100}>
                                <Pie
                                    data={chartData}
                                    dataKey="value"
                                    innerRadius={25}
                                    outerRadius={45}
                                    startAngle={90}
                                    endAngle={-270}
                                >
                                    <Cell key="value" fill={color} />
                                    <Cell key="other" fill="#e5e7eb" />
                                </Pie>
                            </PieChart>
                        </div>
                    </div>
                );
            })}
        </div>
    );
}

export default StatusOverView;
