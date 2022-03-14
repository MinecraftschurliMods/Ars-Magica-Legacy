function parseToNodeList(html) {
  let element = document.createElement('template');
  element.innerHTML = html;
  return element.content.childNodes;
}

async function loadContent(content_json_url, content_folder, target_container_selector, content_template_id, nav_list_selector) {
  const sections = await fetch(content_json_url).then(value => value.json());
  const target = document.querySelector(target_container_selector);
  let nav_list;
  if (nav_list_selector) {
    nav_list = document.querySelector(nav_list_selector);
  }
  for (const section of sections) {
    const element = document.getElementById(content_template_id).content.firstElementChild.cloneNode(true);
    if (!section.small) {
      element.classList.add("max");
    }
    element.id = section.id;
    console.log(element);
    let heading = element.querySelector('.heading');
    heading.innerText = section.title || section.name;
    heading.classList.remove('heading');
    if (heading.classList.length === 0) {
      heading.removeAttribute('class');
    }
    element.querySelector('.body').replaceWith(...(await fetch(content_folder+section.id+'.html').then(value => value.text()).then(value => parseToNodeList(value))));
    target.appendChild(document.createComment(' '+section.name+' '));
    target.appendChild(element);
    element.querySelectorAll('script').forEach(value => eval(value.text));

    if (nav_list) {
      const item = document.createElement('li');
      const link = document.createElement('a');
      link.href = '#' + section.id;
      link.innerText = section.name;
      item.appendChild(link);
      nav_list.appendChild(item);
    }
  }
}
