import {useEffect, useState} from "react";
import axios from "axios";
import {MapContainer, TileLayer, Tooltip, CircleMarker} from "react-leaflet";
import 'leaflet/dist/leaflet.css';

function HQBranchMap() {

    // 대리점 정보 가져오기
    const [branchData, setBranchData] = useState([]);
    // 네이버 지오코딩 사용하여 주소 -> 위도 경도 변환
    const [loading, setLoading] = useState(true);

    // 대리점 정보 조회
    const getBranchMap = () => {
        return axios
            .get('http://localhost:8080/HQDashBoard/BranchPos')
            .then(res => {
                console.log("서버에서 받은 데이터:", res.data);
                return res.data; // 데이터를 반환
            })
            .catch(err => {
                console.error("에러 발생:", err);
                return []; // 실패해도 빈 배열 반환
            });
    };

    // 지오코딩 호출
    const geoCode = (address) =>{
        return axios.get('http://localhost:8080/proxy/geocode',{
            params: { query : address }
        })
            .then(res => {
                const location = res.data.addresses[0];
                return {
                    lat: parseFloat(location.y),
                    lng: parseFloat(location.x),
                };
            })
            .catch(err => {
                console.error("지오코딩 실패:", address, err);
                return { lat: null, lng: null };
            })
    }

    useEffect(() => {
        // 대리점 데이터 리스트
        getBranchMap().then((list) => {
            // 각 대리점의 주소를 geoCode 로 요청 보냄 -> 위도 경도 좌표 변환,
            // Promise.all : 모든 결과 반환
            Promise.all(
                list.map((branch) =>
                    geoCode(branch.branchRoadAddr).then(({ lat, lng }) => {
                        return { ...branch, lat, lng };
                    })
                )
            )//  Promise.all 성공적으로 종료 - withCoords(대리점 정보 + 위경도) 배열 반환
                .then((withCoords) => {
                    setBranchData(withCoords);
                    setLoading(false);
                })
                .catch((err) => {
                    console.error("지오코딩 병렬 처리 중 오류:", err);
                    setBranchData(list); // 위치 없이 기본 데이터만 저장
                    setLoading(false);
                });
        });
    }, []);

    // 로딩 중엔 간단히 출력
    if (loading) {
        return <div>로딩중...</div>;
    }

    return (
        <div>
            <style>{`
                .leaflet-interactive:focus {
                  outline: none;
                  box-shadow: none;
                }
          `}</style>

            <MapContainer
                // 중심 좌표
                center={[36, 127.5]}
                zoom={7}
                // 지도 크기
                style={{ height: "600px", width: "600px" }}
            >
                <TileLayer
                    url="https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png"
                    attribution='&copy; <a href="https://carto.com/">CartoDB</a> &copy; <a href="https://openstreetmap.org">OpenStreetMap</a> contributors'
                />

                {branchData.map((branch, idx) => (
                    branch.lat && branch.lng && (
                        <CircleMarker
                            key={idx}
                            center={[branch.lat, branch.lng]}
                            radius={8}
                            pathOptions={{
                                color: "#4a90e2",        // 외곽선
                                fillColor: "#4a90e2",    // 내부 색
                                fillOpacity: 0.6,
                                weight: 2,
                            }}
                            eventHandlers={{
                                mouseover: (e) => {
                                    e.target.setStyle({
                                        fillOpacity: 0.9,
                                        radius: 10,
                                    });
                                },
                                mouseout: (e) => {
                                    e.target.setStyle({
                                        fillOpacity: 0.6,
                                        radius: 8,
                                    });
                                },
                            }}
                        >
                            <Tooltip direction="top" offset={[0, -8]} opacity={1} >
                                <strong>{branch.branchName}</strong><br/>
                                {branch.branchPhone}<br/>
                                {branch.branchRoadAddr}<br/>
                            </Tooltip>
                        </CircleMarker>
                    )
                ))}
            </MapContainer>
        </div>
    );
}

export default HQBranchMap