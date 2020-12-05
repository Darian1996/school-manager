rm -rf .git
git init
git remote add gitee   git@gitee.com:Darian1996/school-manager.git
git remote add github  git@github.com:Darian1996/school-manager.git 
git add .
git commit -m "init"
git push -f gitee master
git push -f github master