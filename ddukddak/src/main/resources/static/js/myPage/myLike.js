
//페이지 로드 시 집들이 목록 불러오기 + 옵션 선택 하면 해당 목록 불러오도록
function onload(){

    loadHouseBoards();

    //옵션에서 가져온 값에 따라 비동기로 내가 좋아요를 누른 해당 boardCode의 게시글 목록을 가져와야 함
    document.querySelector(".select-box").addEventListener("change", function() {
        const boardCode = this.value;
        console.log(boardCode);
        if(boardCode == 1){ //집들이
            loadHouseBoards();
        }else{ //노하우
            loadTipBoards();
        }
    })
}



function loadHouseBoards() {
    console.log("집들이 불러오기");
    fetch("/myCommunity/House")
    .then(resp => resp.text())
    .then(result =>{
        console.log(result);

        if(result != null){
                
            const boardList = JSON.parse(result);
            console.log("집들이",boardList);


            //게시글 목록 본문
            const postArea = document.querySelector("#postArea");
            postArea.innerHTML = "";


            const ul = document.createElement("ul");
            ul.classList.add("homeBoard-list");

            boardList.forEach((board) => {
                const li = document.createElement("li");
                li.classList.add("myHouseBoardList");

                ul.append(li);

                const img = document.createElement("img");
                img.classList.add("homeBoard-img");
                img.src = board.thumbnail;

                const h3 =document.createElement("h3");
                h3.classList.add("homeboard-title");
                h3.innerHTML = board.boardTitle;
                h3.href = `myHouse/detail/${board.boardNo}`;


                li.append(img, h3);
                
                const div1 = document.createElement("div");
                div1.classList.add("homeBoard-info");

                const div2 = document.createElement("div");
                const a2 = document.createElement("a");
                a2.innerText = "방문하기>";
                a2.href = `myHouse/detail/${board.boardNo}`;
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

            
            const defaultThumbnail = "/images/board/myHouse/20240608183317_00001.jpg";



            // 빈 배열을 생성하여 썸네일들을 저장
            var thumbnails = [];

            // 파일의 존재 여부를 확인하는 함수
            function checkImageExists(url, callback) {
                var img = new Image();  //1번
                img.onload = function() { //비동기적으로 계속 확인중
                    callback(true);
                };
                img.onerror = function() {
                    callback(false);
                };
                img.src = url; //url 설정 이후에 onload와 onerror 확인됨 -2번
            }


            boardList.forEach(board => {
                let thumbnailPath = board.thumbnail;
                thumbnails.push(thumbnailPath);
            })
            thumbnails.forEach(
                function(url, index){
                    checkImageExists(url, function(exists){
                        if(!exists){
                            document.querySelectorAll('.homeBoard-img')[index].src = defaultThumbnail;
                        }
                    })
                }
            )

        }else{
            const div =document.createElement("div");
            div.classList.add("noHouseData");
            div.innerText=" 좋아요 한 게시글이 존재하지 않습니다.";
            postArea.append(div);

        }
    });
}

function loadTipBoards() {

    fetch("/myCommunity/Tip")
    .then(resp => resp.text())
    .then(result =>{

        if(result != null){

            const myTipBoardList = JSON.parse(result);
            console.log("노하우",myTipBoardList);


            //게시글 목록 본문
            const postArea = document.querySelector("#postArea");
            postArea.innerHTML = "";

            const table = document.createElement('table');
            table.className = 'tipBoard-table';
            const tbody = document.createElement('tbody');
            postArea.append(table);
            table.appendChild(tbody);

            const rows = Math.ceil(myTipBoardList.length / 3);
            console.log(rows);


            for (let rowIndex = 0; rowIndex < rows; rowIndex++) {
                const tr = document.createElement('tr');
                tr.className = 'tipBoard-row';

                for (let colIndex = 0; colIndex < 3; colIndex++) {
                    const listIndex = rowIndex * 3 + colIndex;

                    if (listIndex < myTipBoardList.length) {
                        const td = document.createElement('td');
                        const section = document.createElement('section');
                        section.className = 'tipBoard-item';

                        const img = document.createElement('img');
                        img.className = 'tipBoard-img';
                        img.src = myTipBoardList[listIndex].thumbnail;
                        section.appendChild(img);

                        const userSpan = document.createElement('span');
                        userSpan.textContent = '@' + myTipBoardList[listIndex].memberNickname;
                        section.appendChild(userSpan);

                        const titleSpan = document.createElement('span');
                        titleSpan.textContent = myTipBoardList[listIndex].boardTitle;
                        section.appendChild(titleSpan);

                        const likeSpan = document.createElement('span');
                        const likeIcon = document.createElement('i');
                        likeIcon.className = 'fa-solid fa-heart';
                        const likeCountSpan = document.createElement('span');
                        likeCountSpan.className = 'likeCount';
                        likeCountSpan.textContent = myTipBoardList[listIndex].likeCount;
                        likeSpan.appendChild(likeIcon);
                        likeSpan.appendChild(likeCountSpan);
                        section.appendChild(likeSpan);

                        const contentSpan = document.createElement('span');
                        contentSpan.textContent = myTipBoardList[listIndex].boardContent;
                        section.appendChild(contentSpan);

                        td.appendChild(section);
                        tr.appendChild(td);
                    }
                }
                tbody.appendChild(tr);
            }

            
            const defaultThumbnail = "/images/board/myHouse/20240608183317_00001.jpg";



            // 빈 배열을 생성하여 썸네일들을 저장
            var thumbnails = [];

            // 파일의 존재 여부를 확인하는 함수
            function checkImageExists(url, callback) {
                var img = new Image();  //1번
                img.onload = function() { //비동기적으로 계속 확인중
                    callback(true);
                };
                img.onerror = function() {
                    callback(false);
                };
                img.src = url; //url 설정 이후에 onload와 onerror 확인됨 -2번
            }


            myTipBoardList.forEach(board => {
                let thumbnailPath = board.thumbnail;
                thumbnails.push(thumbnailPath);
            })
            thumbnails.forEach(
                function(url, index){
                    checkImageExists(url, function(exists){
                        if(!exists){
                            document.querySelectorAll('.homeBoard-img')[index].src = defaultThumbnail;
                        }
                    })
                }
            )

        }else{
            const div =document.createElement("div");
            div.classList.add("noHouseData");
            div.innerText=" 좋아요 한 게시글이 존재하지 않습니다.";
            postArea.append(div);
        }
    });
}















onload();
