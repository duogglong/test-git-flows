import subprocess
import re

# Thay bằng tên user bạn muốn lọc
target_author = "duogglong"

# Lấy danh sách các file bị thay đổi so với origin/master
files_changed = subprocess.check_output(
    ["git", "diff", "--name-only", "origin/master..HEAD"]
).decode().splitlines()

for file_path in files_changed:
    try:
        # Lấy diff dạng unified để biết được dòng nào thêm vào
        diff = subprocess.check_output(
            ["git", "diff", "origin/master..HEAD", "--", file_path]
        ).decode()

        added_lines = []

        # Tìm các dòng thêm mới bắt đầu bằng "+"
        for line in diff.splitlines():
            if line.startswith("+") and not line.startswith("+++"):
                added_lines.append(line[1:])  # Bỏ ký tự +

        if not added_lines:
            continue

        # Lấy git blame của file
        blame_output = subprocess.check_output(
            ["git", "blame", "--line-porcelain", "HEAD", "--", file_path]
        ).decode()

        blame_lines = blame_output.splitlines()

        show_file = False
        blame_line_iter = iter(blame_lines)

        # Đọc từng khối blame (mỗi dòng blame là 12 dòng, nhưng chỉ quan tâm "author" và "summary")
        current_line = None
        blame_info = {}

        for line in blame_line_iter:
            if re.match(r'^[0-9a-f]{40}', line):
                current_line = line
                blame_info = {}
                continue
            if line.startswith("author "):
                blame_info['author'] = line[7:]
            if line.startswith("summary "):
                blame_info['summary'] = line[8:]

            if line.strip() == "":
                continue  # End of this blame block

            if 'author' in blame_info and blame_info['author'] == target_author:
                for added_line in added_lines:
                    if added_line.strip() in current_line:
                        show_file = True
                        break

            if show_file:
                break

        if show_file:
            print(f"\n--- {file_path} ---")
            for line in diff.splitlines():
                if line.startswith("@@"):
                    print(line)
                elif line.startswith("+") and not line.startswith("+++"):
                    # Xác định blame cho dòng + đó
                    print(line)

    except subprocess.CalledProcessError:
        continue
