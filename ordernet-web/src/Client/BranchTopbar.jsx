import {Home, LogOut} from "lucide-react";

function BranchTopbar({title}) {
  return (
      <nav className="navbar navbar-light justify-content-between px-4 border-bottom"
           style={{height: "60px", backgroundColor: '#343a40'}}>
            <span
                className="px-3 py-1 fw-bold"
                style={{
                  backgroundColor: '#ffffff',
                  color: '#333',
                  borderTopLeftRadius: '5px',
                  borderTopRightRadius: '5px',
                  border: '1px solid white',
                  height: "42px",
                  fontSize: '14px',
                  display: 'inline-block',
                  borderBottom: 'none',
                  marginTop: '15px'
                }}
            >
              {title}
            </span>

        <div className="d-flex align-items-center gap-3">
          <span className="me-2 text-white">LOGOUT</span>
          <span className="fw-bold text-white">홍길동 님</span>
          <Home className="me-2 cursor-pointer text-white"/>
          <LogOut className="cursor-pointer text-white"/>
        </div>
      </nav>
  );
}

export default BranchTopbar
