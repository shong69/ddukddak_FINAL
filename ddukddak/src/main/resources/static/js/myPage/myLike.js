
//페이지 로드 시 집들이 목록 불러오기 + 옵션 선택 하면 해당 목록 불러오도록
function onload(){
    loadHouseBoards();
    //옵션에서 가져온 값에 따라 비동기로 내가 좋아요를 누른 해당 boardCode의 게시글 목록을 가져와야 함
    var selectBoardCode = function(value){
        const boardCode = value;
        //보드 코드랑 로그인멤버의 멤버 넘버 넘겨서 비동기로 객체 배열 가져오기
        console.log(value);

        if(value == 1){ //집들이
            loadHouseBoards();
        }else{ //노하우
            loadTipBoards();
        }
    }

}


function loadHouseBoards() {
    fetch("myCommunity/myLikes/House")
    .then(resp => resp.json())
    .then(result =>{
        
        if(result && result.length > 0){
            
                
            const boardList = result;
            console.log(boardList);

            //게시글 목록 본문
            const postArea = document.querySelector("#postArea");
            postArea.innerHTML = "";

            boardList.array.forEach(board => {

                const ul = document.createElement("ul");
                ul.classList.add("homeBoard-list");

                const li = document.createElement("li");
                li.classList.add("myHouseBoardList");

                ul.append(li);

                const img = document.createElement("img");
                img.classList.add("homeBoard-img");
                img.src = board.thumbnail;

                const h3 =document.createElement("h3");
                h3.classList.add("homeboard-title");
                h3.innerHTML = board.boardTitle;
                
                const a =document.createElement("a");
                a.href = `myHouse/detail/${board.boardNo}`;
                h3.append(a);

                li.append(img, h3);
                const div1 = document.createElement("div");
                div1.classList.add("homeBoard-info");

                const div2 = document.createElement("div");
                const a2 = document.createElement("a");
                a.innerText = "방문하기";
                a.href = `myHouse/detail/${board.boardNo}`;
                div2.append(a2);

                const div3 = document.createElement("div");
                const span1 =document.createElement("span");
                const span2 =document.createElement("span");
                const span3 =document.createElement("span");
                span1.innerText = board.memberNickname;
                span2.innerText = board.readCount;
                if(board.boardUpdateDate != null){
                    span3.innerText = board.boardUpdateDate;
                }else{
                    span3.innerText = board.boardWriteDate;
                }

                div3.append(span1,span2,span3);
                div1.append(div2, div3);
                li.append(div1);
            });

            postArea.append(ul);

        }else{
            const div =document.createElement("div");
            div.classList.add("noHouseData");
            div.innerText=" 좋아요 한 게시글이 존재하지 않습니다.";
            postArea.append(div);
        }
    });
}

function loadTipBoards() {
    fetch("myCommunity/mylikes/Tip")
    .then(resp => resp.text())
    .then(result =>{
        if(result == 1){

        }else{
            
        }
    });
}















onload();