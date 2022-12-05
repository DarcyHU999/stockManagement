import './App.css';
import React from 'react';
import { BrowserRouter ,Router,Routes, Link,Route} from "react-router-dom";
import Home from './page/Home'
import 'antd/dist/antd.css';
function App() {
  return (
      <div className="App">
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home/>} />

          </Routes>
        </BrowserRouter>

      </div>
  );
}

export default App;
