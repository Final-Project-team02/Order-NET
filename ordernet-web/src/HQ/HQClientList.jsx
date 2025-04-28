function HQClientList() {

    const data = [
        { clientName: 'Order Net', clientCeoName: '하므로', clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '경민', clientId: 'AD123DB', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '이동윤', clientId: 'AD123DC', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '이의진',clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '정민',clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '노종근',clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
    ]


    return (
        <div>
            <div className={'mt-3 py-3'} style={{backgroundColor: "#CFE2FF"}}>
                <p className={'fw-bold px-3 m-0'}>대리점 리스트</p>
            </div>
            <div style={{ height: '80vh', display: 'flex', justifyContent: 'center', flexDirection: 'column' }}>
                <table className="table table-bordered text-center ">
                    <colgroup>
                        <col width={'5%'}/>
                        <col width={'15%'}/>
                        <col width={'15%'}/>
                        <col width={'15%'}/>
                        <col width={'20%'}/>
                        <col width={'30%'}/>
                    </colgroup>
                    <thead>
                    <tr style={{ border: "none"}}>
                        <th style={{ border: "none"}}>선택</th>
                        <th style={{ border: "none"}}>대리저명</th>
                        <th style={{ border: "none"}}>대표자명</th>
                        <th style={{ border: "none"}}>대리점고유ID</th>
                        <th style={{ border: "none"}}>대리점 전화번호< /th>
                        <th style={{ border: "none"}}>대리점 주소</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.map((datas,t) =>(
                        <tr key={t}>
                            <td><input type="checkbox" className={'form-check-input'} value={''} id={'client_id'}/></td>
                            <td>{datas.clientName}</td>
                            <td>{datas.clientCeoName}</td>
                            <td>{datas.clientId}</td>
                            <td>{datas.clientPhone}</td>
                            <td>{datas.clientAddr}</td>
                        </tr>
                    ))}
                    {/*<tr>*/}
                    {/*    <td><input type="checkbox" className={'form-check-input'} value={''} id={'client_id'}/></td>*/}
                    {/*    <td>Order Net</td>*/}
                    {/*    <td>이의진</td>*/}
                    {/*    <td>AD123DS</td>*/}
                    {/*    <td>010-4043-1692</td>*/}
                    {/*    <td>부산시 진구 중앙대로</td>*/}
                    {/*</tr>*/}
                    </tbody>
                </table>
                <div className={'mt-5'}>
                    <hr/>
                    <div className={'text-end'}>
                        <button className={'btn'} style={{backgroundColor:"#CFE2FF"}} type={"button"}>추가</button>
                        <button className={'mx-2 btn'} style={{backgroundColor:"#CFE2FF"}} type={"button"}>수전</button>
                        <button className={'btn'} style={{backgroundColor:"#FFB6B3"}} type={"button"}>삭제</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HQClientList

