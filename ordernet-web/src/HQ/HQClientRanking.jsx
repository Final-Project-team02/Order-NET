import React, {useEffect, useState} from "react";
import HQTopbar from "./HQTopbar.jsx";
import HQSidebarMenu from "./HQSidebarMenu.jsx";
import Title from "../layout/Title.jsx";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    ComposedChart,
    ResponsiveContainer
} from "recharts";
import axios from "axios";



// const data = [
//     {name: 'Page A', pv: 800},
//     {name: 'Page B', pv: 967},
//     {name: 'Page C', pv: 1098},
//     {name: 'Page D', pv: 1200},
//     {name: 'Page E', pv: 1108},
//     {name: 'Page F', pv: 680},
//     {name: 'Page K', pv: 680},
// ];


function HQClientRanking() {
    const [rankingData, setRankingData] = useState([]);
    const [productData, setProductData] = useState([]);

    const menuItems = [
        { text: "본사 대시보드 ", link: "/" },
        { text: "주문 확정", link: "/HQMain" },
        { text: "대리점 관리", link: "/HQClientList" },
        { text: "재고현황", link: "/HQStockStatus" },
    ];

    useEffect(() => {
        axios.get('http://localhost:8080/HQMain/productranking')
            .then(response =>{
                setProductData(response.data);
            })
            .catch(error => {
                console.log("오류가 났음", error);
            });
    }, []);

    // API 호출 (useEffect)
    useEffect(() => {
        // 백엔드에서 대리점 순위 데이터 가져오기
        axios.get('http://localhost:8080/HQMain/clientranking')
            .then(response => {
                // 받아온 데이터를 rankingData에 저장
                setRankingData(response.data);
            })
            .catch(error => {
                console.log("대리점 순위 데이터를 가져오는 중 오류가 발생했습니다:", error);
            });
    }, []);


    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="대리점 순위"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>
                    <Title breadcrumb="☆ 대리점 순위 > 대리점 리스트" panelTitle="대리점 순위"/>

                    <div className={'container mt-5 border border-2 p-3'}>
                        <div className={'row'}>
                            <div className={'col-6 text-center'}>
                                <div className={'my-5'} style={{ height: 500, width: 550 }}>
                                    <ResponsiveContainer width="100%" height="100%">
                                        <BarChart
                                            data={rankingData}
                                            layout="vertical"
                                            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
                                            <CartesianGrid strokeDasharray="3 3" />
                                            <XAxis type="number" />
                                            <YAxis dataKey="branchId" type="category" />
                                            <Tooltip />
                                            <Legend />
                                            <Bar dataKey="orderPrice" fill="#413ea0" />
                                        </BarChart>
                                    </ResponsiveContainer>
                                </div>
                                <p style={{ fontWeight: "bold" }}>지점별 매출 순위 Top7</p>
                            </div>

                            <div className={'col-6 text-center'}>
                                <div className={'my-5'} style={{ height: 500, width: 550 }}>
                                    <ResponsiveContainer width="100%" height="100%">
                                        <ComposedChart
                                            data={productData}
                                            margin={{ top: 20, right: 20, bottom: 20, left: 20 }}
                                        >
                                            <CartesianGrid stroke="#f5f5f5" />
                                            <XAxis dataKey="partId" scale="band" />
                                            <YAxis />
                                            <Tooltip />
                                            <Legend />
                                            <Bar dataKey="orderItemQuantity" barSize={20} fill="#413ea0" />
                                        </ComposedChart>
                                    </ResponsiveContainer>
                                </div>
                                <p style={{ fontWeight: "bold" }}>제품 발주 Top5</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HQClientRanking;
