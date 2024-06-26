

// ul 태그(댓글 목록 감싸는 요소)
const ul = document.getElementById("commentList");

const selectCommentList = () => {
  fetch("/comment?boardNo=" + boardNo)
    .then(resp => resp.json())
    .then(commentList => {
      console.log(commentList);

      ul.innerHTML = ""; // 기존 댓글 목록 삭제

      // commentList 안에 comment 에 각각 접근하는 반복문
      for (let comment of commentList) {
        // 행 생성
        const commentContainer = document.createElement("li");
        commentContainer.classList.add("commentContainer");

        if (comment.parentCommentNo != 0) {
          commentContainer.classList.add("child-comment");
        }

        if (comment.commentDelFl == 'Y') {
          commentContainer.innerText = "삭제된 댓글입니다.";
        } else {
          // 작성자 영역 포함하는 div 생성, class 추가
          const commentUser = document.createElement("div");
          commentUser.classList.add("commentUser");

          // 프로필 이미지, 닉네임, 날짜 감싸는 요소
          const userInfo = document.createElement("div");
          userInfo.classList.add("userInfo");

          commentUser.append(userInfo);

          const profileImg = document.createElement("img");
          profileImg.src = comment.profileImg ? comment.profileImg : userDefaultImage;

          // 닉네임
          const nickname = document.createElement("h3");
          nickname.innerText = comment.memberNickname;

          // 날짜(작성일)
          const commentDate = document.createElement("h4");
          commentDate.innerText = comment.commentWriteDate;

          // 작성자 영역(userInfo) 에 프로필 닉네임 날짜 추가
          userInfo.append(profileImg, nickname, commentDate);

          // 댓글 + 버튼 Area 담을 div 생성, class 추가
          const commentContentArea = document.createElement("div");
          commentContentArea.classList.add("commentContentArea");

          // 댓글 담을 div 생성, class 추가
          const displayComment = document.createElement("div");
          displayComment.classList.add("displayComment");

          // 댓글 내용
          const commentContent = document.createElement("h3");
          commentContent.classList.add("commentContent");
          commentContent.innerText = comment.commentContent;

          displayComment.append(commentContent);

          // 버튼 담을 div 생성, class 추가
          const commentBtnArea = document.createElement("div");
          commentBtnArea.classList.add("commentBtnArea");

          // 로그인한 회원번호가 댓글 작성자 회원번호와 같을 때
          if (loginMemberNo != null && loginMemberNo == comment.memberNo) {
            // 수정 버튼
            const updateBtn = document.createElement("div");
            updateBtn.classList.add("parentBtn");
            updateBtn.innerText = "수정";
            updateBtn.setAttribute("onclick", `showUpdateComment(${comment.commentNo}, this)`);

            // 삭제 버튼
            const deleteBtn = document.createElement("div");
            deleteBtn.classList.add("parentBtn");
            deleteBtn.innerText = "삭제";
            deleteBtn.setAttribute("onclick", `deleteComment(${comment.commentNo})`);

            // 버튼 영역에 수정, 삭제 버튼 추가
            commentBtnArea.append(updateBtn, deleteBtn);
          }

          // 답글 버튼
          const childCommentBtn = document.createElement("div");
          childCommentBtn.classList.add("parentBtn");
          childCommentBtn.innerText = "답글";
          childCommentBtn.setAttribute("onclick", `showInsertComment(${comment.commentNo}, this)`);

          commentBtnArea.append(childCommentBtn);

          // 영역 합치기
          commentContentArea.append(displayComment, commentBtnArea);
          commentContainer.append(commentUser, commentContentArea);
        }

        ul.append(commentContainer);
      }
    })
    .catch(error => console.error('Error fetching comments:', error));
}

// 댓글 등록(Ajax)
const insertComment = document.querySelector("#insertComment");
const inputCommentContent = document.querySelector("#inputCommentContent");
const commentCount = document.querySelector("#commentCount");

if (insertComment != null) {
  insertComment.addEventListener("click", () => {
    if (loginMemberNo == null) {
      alert("로그인 후 이용해주세요.");
      redirectToLogin();
      return;
    }

    if (inputCommentContent.value.trim().length == 0) {
      alert("댓글 내용을 입력해주세요.");
      inputCommentContent.focus();
      return;
    }

    const data = {
      "commentContent": inputCommentContent.value,
      "boardNo": boardNo,
      "memberNo": loginMemberNo
    };

    fetch("/comment/listAndCount", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    })
      .then(resp => resp.json())
      .then(result => {
        if (result.success) {
          alert("댓글이 등록되었습니다.");
          inputCommentContent.value = "";
          commentCount.innerText = `댓글 ${result.count}개`;
          selectCommentList();
        } else {
          alert("댓글 등록에 실패하였습니다.");
        }
      })
      .catch(err => console.error('Error:', err));
  });
}

// 답글 작성 화면 추가
const showInsertComment = (parentCommentNo, area) => {
  if (loginMemberNo == null) {
    alert("로그인이 필요한 서비스입니다.");
    redirectToLogin();
    return;
  }

  const temp = document.getElementsByClassName("childCommentArea");

  if (temp.length > 0) {
    if (confirm("다른 답글을 작성 중... 현재 댓글에 답글을 작성하시겠습니까?")) {
      temp[0].nextElementSibling.remove();
      temp[0].remove();
    } else {
      return;
    }
  }

  const childCommentArea = document.createElement("div");
  childCommentArea.classList.add("childCommentArea");

  const textarea = document.createElement("textarea");
  textarea.classList.add("childCommentInsertContent");

  childCommentArea.append(textarea);
  area.parentElement.parentElement.after(childCommentArea);

  const commentBtnArea = document.createElement("div");
  commentBtnArea.classList.add("childCommentBtnArea");

  const insertBtn = document.createElement("div");
  insertBtn.classList.add("childBtn");
  insertBtn.innerText = "등록";
  insertBtn.setAttribute("onclick", "insertChildComment(" + parentCommentNo + ", this)");

  const cancelBtn = document.createElement("div");
  cancelBtn.classList.add("childBtn");
  cancelBtn.innerText = "취소";
  cancelBtn.setAttribute("onclick", "insertCancel(this)");

  commentBtnArea.append(insertBtn, cancelBtn);
  childCommentArea.after(commentBtnArea);
}

// 답글 (자식 댓글) 작성 취소
const insertCancel = (cancelBtn) => {
  cancelBtn.parentElement.previousElementSibling.remove();
  cancelBtn.parentElement.remove();
}

// 답글 (자식 댓글) 등록
const insertChildComment = (parentCommentNo, btn) => {
  const textarea = btn.parentElement.previousElementSibling.querySelector(".childCommentInsertContent");

  if (textarea.value.trim().length == 0) {
    alert("답글 내용 작성 후 등록 버튼을 클릭해 주세요.");
    textarea.focus();
    return;
  }

  const data = {
    "commentContent": textarea.value,
    "boardNo": boardNo,
    "memberNo": loginMemberNo,
    "parentCommentNo": parentCommentNo
  };

  fetch("/comment", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
    .then(resp => resp.json())
    .then(result => {
      if (result.success) {
        selectCommentList();
      } else {
        alert("답글 등록에 실패하였습니다.");
      }
    })
    .catch(err => console.error('Error:', err));
}

// 댓글 수정 화면 보여주기
const showUpdateComment = (commentNo, element) => {

  const commentContainer = element.closest('.commentContainer');
  const commentContent = commentContainer.querySelector('.commentContent');
  const originalContent = commentContent.innerText;

  const textarea = document.createElement('textarea');
  textarea.classList.add('commentUpdateContent');
  textarea.value = originalContent;

  const updateBtn = document.createElement('button');
  updateBtn.classList.add("myPageMainBtn")
  updateBtn.innerText = '수정';
  updateBtn.onclick = () => updateComment(commentNo, textarea.value);

  const cancelBtn = document.createElement('button');
  cancelBtn.classList.add("myPageMainBtn")
  cancelBtn.innerText = '취소';
  cancelBtn.onclick = () => {
    commentContent.style.display = 'block';
    textarea.remove();
    updateBtn.remove();
    cancelBtn.remove();
  }

  commentContent.style.display = 'none';
  commentContent.after(textarea, updateBtn, cancelBtn);
}

// 댓글 수정
const updateComment = (commentNo, content) => {
  if (content.trim().length == 0) {
    alert("댓글 내용을 입력해주세요.");
    return;
  }

  const data = {
    "commentNo": commentNo,
    "commentContent": content
  };

  fetch("/comment/update", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
    .then(resp => resp.json())
    .then(result => {
      if (result.success) {
        selectCommentList();
      } else {
        alert("댓글 수정에 실패하였습니다.");
      }
    })
    .catch(err => console.error('Error:', err));
}

// 댓글 삭제
const deleteComment = (commentNo) => {
  if (!confirm("댓글을 삭제하시겠습니까?")) {
    return;
  }

  fetch("/comment/delete?commentNo=" + commentNo, {
    method: "DELETE"
  })
    .then(resp => resp.json())
    .then(result => {
      if (result.success) {
        selectCommentList();
      } else {
        alert("댓글 삭제에 실패하였습니다.");
      }
    })
    .catch(err => console.error('Error:', err));
}

function redirectToLogin() {
  const currentUrl = window.location.href;
  window.location.href = `/member/login?returnUrl=${encodeURIComponent(currentUrl)}`;
}

// document.addEventListener('DOMContentLoaded', () => {
//   selectCommentList();
// });
