from typing import Set, Any
import os
import json


def load_csv_problems(csv_path: str) -> Set[str]:
    with open(csv_path, "r", encoding="utf-8") as f:
        return set(line.strip() for line in f if line.strip())


def extract_ids(obj: Any, ids: Set[str]) -> None:
    if isinstance(obj, dict):
        for v in obj.values():
            extract_ids(v, ids)
        if "problem_id" in obj:
            ids.add(str(obj["problem_id"]))
    elif isinstance(obj, list):
        for item in obj:
            extract_ids(item, ids)


def collect_json_problem_ids(root_dir: str) -> Set[str]:
    problem_ids: Set[str] = set()
    for subdir in os.listdir(root_dir):
        sub_path = os.path.join(root_dir, subdir)
        if os.path.isdir(sub_path):
            json_path = os.path.join(sub_path, "meta.json")
            if os.path.isfile(json_path):
                with open(json_path, "r", encoding="utf-8") as jf:
                    data = json.load(jf)
                    extract_ids(data, problem_ids)
    return problem_ids


def main() -> None:
    root_dir = os.path.dirname(os.path.abspath(__file__))
    csv_path = os.path.join(root_dir, "meta.csv")
    csv_ids = load_csv_problems(csv_path)
    used_ids = collect_json_problem_ids(root_dir)
    unused_ids = sorted(csv_ids - used_ids, key=int)
    for pid in unused_ids:
        print(pid)


if __name__ == "__main__":
    main()
