

function Title({ panelTitle, breadcrumb }) {


    return (
        <div>
            <div className="bg-white mb-3 ms-2">
                <div className="text-dark small">{breadcrumb}</div>
            </div>

            <div
                className="p-3  bg-opacity-10 border d-flex align-items-center gap-3"
                style={{ flexWrap: 'nowrap', overflowX: 'auto' , backgroundColor: '#CFE2FF' }}>
                <label className="d-flex align-items-center flex-nowrap">
          <span className="fw-bold me-2" style={{ whiteSpace: 'nowrap' }}>
            {panelTitle}
          </span>
                </label>
            </div>
        </div>
    );
}

export default Title;
