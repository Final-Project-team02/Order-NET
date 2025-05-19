import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
    AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer
} from 'recharts';

const branchColors = [
    '#a8dadc', '#ffb4a2', '#cdb4db', '#90be6d',
    '#f4a261', '#b5ead7', '#e5989b', '#6d6875',
];

const getColorForBranch = (branch, index) => branchColors[index % branchColors.length];

// 월별 지점별 데이터 변환
const transformData = (rawData) => {
    const monthMap = {};

    rawData.forEach(({ orderMonth, branchId, totalSales }) => {
        const monthNum = orderMonth.split('-')[1].replace(/^0/, '');
        if (!monthMap[orderMonth]) {
            monthMap[orderMonth] = {
                orderMonth,
                month: `${monthNum}월`,
            };
        }
        monthMap[orderMonth][branchId] = totalSales;
    });

    return Object.values(monthMap);
};

const MonthlySalesChart = () => {
    const [data, setData] = useState([]);
    const [branches, setBranches] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/HQDashBoard/order/monthly-sales')
            .then(response => {
                const rawData = response.data;

                if (!rawData || rawData.length === 0) {
                    setData([]);
                    setBranches([]);
                    return;
                }

                const transformedData = transformData(rawData);

                // 모든 월에 포함된 지점 ID 추출 (중복 제거)
                const branchSet = new Set();
                transformedData.forEach(row => {
                    Object.keys(row).forEach(key => {
                        if (!['orderMonth', 'month'].includes(key)) {
                            branchSet.add(key);
                        }
                    });
                });

                setBranches([...branchSet]);
                setData(transformedData);
            })
            .catch(err => {
                console.error("데이터 로딩 실패:", err);
            });
    }, []);

    return (
        <div  style={{
            padding: '1.5rem',
            background: '#fff',
            borderRadius: '12px',
            boxShadow: '0 4px 12px rgba(0, 0, 0, 0.06)',
            marginBottom: '2rem',
        }}>
            <h3 style={{
                textAlign: 'center',
                marginTop:'28px',
                marginBottom: '1rem',
                fontWeight: '600',
                fontSize: '1.2rem',
                color: '#333'
            }}>
                지점별 월간 매출 추이
            </h3>
            <ResponsiveContainer width="100%"   height={348} >
                <AreaChart data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis
                        dataKey="month"
                        angle={-30}
                        textAnchor="end"
                        interval={0}
                    />
                    <YAxis  tickFormatter={(value) => {
                        if (value >= 1_0000) return `${value / 1_0000}만`;
                        return value;
                    }}/>
                    <Tooltip
                        content={({ payload, label }) => {
                            if (!payload || payload.length === 0) return null;
                            return (
                                <div style={{ background: '#fff', border: '1px solid #ccc', padding: 10 }}>
                                    <strong>{label}</strong>
                                    <ul style={{ listStyle: 'none', padding: 0, margin: 0 }}>
                                        {payload.map(entry => (
                                            <li key={entry.dataKey} style={{ color: entry.color }}>
                                                {entry.name}: {entry.value.toLocaleString()}원
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            );
                        }}
                    />
                    <Legend verticalAlign="top"
                            height={30}
                            wrapperStyle={{
                                fontSize: '12px',
                                whiteSpace: 'nowrap',
                                overflowX: 'auto',
                                display: 'flex',
                                flexWrap: 'nowrap',
                                justifyContent: 'center'
                            }} />
                    {branches.map((branch, idx) => (
                        <Area
                            key={branch}
                            type="monotone"
                            dataKey={branch}
                            name={branch}
                            stroke={getColorForBranch(branch, idx)}
                            fill={getColorForBranch(branch, idx)}
                            fillOpacity={0.15}
                            isAnimationActive={false}
                        />
                    ))}
                </AreaChart>
            </ResponsiveContainer>
        </div>
    );
};

export default MonthlySalesChart;
